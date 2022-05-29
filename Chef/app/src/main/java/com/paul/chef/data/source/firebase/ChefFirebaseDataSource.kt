package com.paul.chef.data.source.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.MsgType
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefDataSource
import com.paul.chef.data.source.Result
import com.paul.chef.util.ConstValue.ADDRESS
import com.paul.chef.util.ConstValue.ATTENDANCE
import com.paul.chef.util.ConstValue.BLOCK_MENU_LIST
import com.paul.chef.util.ConstValue.BLOCK_REVIEW_LIST
import com.paul.chef.util.ConstValue.BOOK_SETTING_CALENDAR_DEFAULT
import com.paul.chef.util.ConstValue.BOOK_SETTING_CHEF_SPACE
import com.paul.chef.util.ConstValue.BOOK_SETTING_TYPE
import com.paul.chef.util.ConstValue.BOOK_SETTING_USER_SPACE
import com.paul.chef.util.ConstValue.CHAT
import com.paul.chef.util.ConstValue.CHEF
import com.paul.chef.util.ConstValue.CHEF_ID
import com.paul.chef.util.ConstValue.DATE
import com.paul.chef.util.ConstValue.DATE_SETTING
import com.paul.chef.util.ConstValue.LAST_DATA_TYPE
import com.paul.chef.util.ConstValue.LAST_MSG
import com.paul.chef.util.ConstValue.LIKE_LIST
import com.paul.chef.util.ConstValue.MENU
import com.paul.chef.util.ConstValue.ORDER
import com.paul.chef.util.ConstValue.PROFILE_INFO
import com.paul.chef.util.ConstValue.PROFILE_INFO_EMAIL
import com.paul.chef.util.ConstValue.REVIEW
import com.paul.chef.util.ConstValue.REVIEW_NUMBER
import com.paul.chef.util.ConstValue.REVIEW_RATING
import com.paul.chef.util.ConstValue.ROOM
import com.paul.chef.util.ConstValue.STATUS
import com.paul.chef.util.ConstValue.TIME
import com.paul.chef.util.ConstValue.TRANSACTION
import com.paul.chef.util.ConstValue.USER
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object ChefFirebaseDataSource : ChefDataSource {

    override suspend fun getMenuList(): Result<List<Menu>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance().collection(MENU)
                .get()
                .addOnSuccessListener { value ->
                    if (value != null) {
                        val dataList = mutableListOf<Menu>()
                        for (i in value.documents) {
                            val item = i.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Menu::class.java)
                            dataList.add(data)
                            if (value.documents.indexOf(i) == value.documents.size - 1) {
                                continuation.resume(Result.Success(dataList))
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override fun getLiveMenuList(): MutableLiveData<List<Menu>> {
        val dataList = mutableListOf<Menu>()
        val liveData = MutableLiveData<List<Menu>>()
        FirebaseFirestore.getInstance().collection(MENU)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Menu::class.java)
                    dataList.add(data)
                    if (value.documents.indexOf(doc) == value.documents.lastIndex) {
                        liveData.value = dataList
                    }
                }
            }
        return liveData
    }

    override suspend fun setUser(profileInfo: ProfileInfo): Result<String> =
        suspendCoroutine { continuation ->
            val id = FirebaseFirestore.getInstance().collection(USER).document().id
            val user = User(id, profileInfo)

            FirebaseFirestore.getInstance().collection(USER).document(id)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Timber.d("DocumentSnapshot added with ID: $documentReference")
                    continuation.resume(Result.Success(id))
                }
                .addOnFailureListener { e ->
                    Timber.w(e, "Error adding document")
                }
        }

    override suspend fun getUserByEmail(email: String): Result<User?> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection(USER)
                .whereEqualTo(PROFILE_INFO_EMAIL, email)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        for (i in value.documents) {
                            val item = i.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, User::class.java)
                            UserManger.user = data
                            if (value.documents.indexOf(i) == value.documents.lastIndex) {
                                continuation.resume(Result.Success((data)))
                            }
                        }
                    } else {
                        continuation.resume(Result.Success((null)))
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override suspend fun getUser(userId: String): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection(USER)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, User::class.java)
                    UserManger.user = data
                    continuation.resume(Result.Success(data))
                } else {
                    Timber.d("No such document")
                }
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    override fun getLiveUser(): MutableLiveData<User> {
        val userId = UserManger.user?.userId
        val liveData = MutableLiveData<User>()
        if(userId!=null){
            FirebaseFirestore.getInstance().collection(USER)
                .document(userId)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Timber.w(e, "Listen failed.")
                        return@addSnapshotListener
                    }
                    val item = value?.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, User::class.java)
                    UserManger.user = data
                    liveData.value = data
                }
        }

        return liveData
    }

    override suspend fun getChef(id: String, isDisplay:Boolean): Result<Chef> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection(CHEF)
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    if(!isDisplay){
                        UserManger.chef = data
                    }

                    continuation.resume(Result.Success(data))
                } else {
                    Timber.d("No such document")
                }
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    override fun getLiveChef(id: String, isDisPlay:Boolean): MutableLiveData<Chef> {
        val liveData = MutableLiveData<Chef>()
        FirebaseFirestore.getInstance().collection(CHEF)
            .document(id)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                val item = value?.data
                val json = Gson().toJson(item)
                val data = Gson().fromJson(json, Chef::class.java)
                if(!isDisPlay){
                    Timber.d("update userManger chef  data")
                    UserManger.chef = data
                }
                liveData.value = data
            }
        return liveData
    }

    override suspend fun getChefIdList(settingType: List<Int>): Result<List<String>> =
        suspendCoroutine { continuation ->
            val chefList = mutableListOf<String>()
            FirebaseFirestore.getInstance().collection(CHEF)
                .whereIn(BOOK_SETTING_TYPE, settingType)
                .get()
                .addOnSuccessListener { value ->
                    if (value != null) {
                        for (i in value.documents) {
                            val item = i.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Chef::class.java)
                            chefList.add(data.id)
                            if (value.documents.indexOf(i) == value.documents.size - 1) {
                                continuation.resume(Result.Success(chefList))
                            }
                        }
                    } else {
                        Timber.d("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override suspend fun blockMenu(blockMenuList: List<String>, userId: String) {
        FirebaseFirestore.getInstance().collection(USER).document(userId)
            .update(
                mapOf(
                    BLOCK_MENU_LIST to blockMenuList,
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun blockReview(blockReviewList: List<String>, userId: String) {
        FirebaseFirestore.getInstance().collection(USER).document(userId)
            .update(
                mapOf(
                    BLOCK_REVIEW_LIST to blockReviewList,
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun createChef(user: User): Result<String> = suspendCoroutine { continuation ->
        val id = FirebaseFirestore.getInstance().collection(CHEF).document().id
        val profileInfo = user.profileInfo
        val userId = UserManger.user?.userId
        if (profileInfo != null && userId != null) {
            val temp = Chef(id, profileInfo)

            FirebaseFirestore.getInstance().collection(CHEF).document(id)
                .set(temp)
                .addOnSuccessListener { documentReference ->
                    Timber.d("DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Timber.w(e, "Error adding document")
                }

            FirebaseFirestore.getInstance().collection("User").document(userId)
                .update(
                    mapOf(
                        CHEF_ID to id
                    )
                )
                .addOnSuccessListener { documentReference ->
                    Timber.d("DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Timber.w(e, "Error adding document")
                }
        }
        continuation.resume(Result.Success(id))
    }

    override suspend fun updateProfile(profileInfo: ProfileInfo, userId: String, chefId: String?) {
        val info = ProfileInfo(
            profileInfo.name,
            profileInfo.email,
            profileInfo.avatar,
            profileInfo.introduce
        )

        if(chefId!=null){
            FirebaseFirestore.getInstance().collection(CHEF).document(chefId)
                .update(
                    mapOf(
                        PROFILE_INFO to info,
                    )
                )
                .addOnSuccessListener {
                    Timber.d("DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
        }



        FirebaseFirestore.getInstance().collection(USER).document(userId)
            .update(
                mapOf(
                    PROFILE_INFO to info,
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun updateAddress(addressList: List<Address>) {
        val userId = UserManger.user?.userId!!
        FirebaseFirestore.getInstance().collection(USER)
            .document(userId)
            .update(
                mapOf(
                    ADDRESS to addressList,
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    override suspend fun setOrder(order: Order): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection(ORDER).document(order.id)
                .set(order)
                .addOnSuccessListener { documentReference ->
                    Timber.d("DocumentSnapshot added with ID: $documentReference")
                    continuation.resume(Result.Success(true))
                }
                .addOnFailureListener { e ->
                    Timber.w(e, "Error adding document")
                }
        }

    override suspend fun updateOrderStatus(status: Int, orderId: String):Result<Boolean> =
        suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection(ORDER).document(orderId)
            .update(
                mapOf(
                    STATUS to status
                )
            )
            .addOnSuccessListener { documentReference ->
                continuation.resume(Result.Success(true))
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.tag("firebaseDataSource").w(e, "Error adding document")
            }
    }

    override suspend fun updateChefReview(
        chefId: String,
        newChefRating: Float,
        newChefRatingNumber: Int,
    ) {
        FirebaseFirestore.getInstance().collection(CHEF).document(chefId)
            .update(
                mapOf(
                    REVIEW_RATING to newChefRating,
                    REVIEW_NUMBER to newChefRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun updateMenuReview(
        menuId: String,
        newMenuRating: Float,
        newMenuRatingNumber: Int,
    ) {
        FirebaseFirestore.getInstance().collection(MENU).document(menuId)
            .update(
                mapOf(
                    REVIEW_RATING to newMenuRating,
                    REVIEW_NUMBER to newMenuRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun updateBookSetting(
        type: Int,
        calendarDefault: Int,
        chefSpace: ChefSpace?,
        userSpace: UserSpace?,
    ) {
        val chefId = UserManger.user?.chefId!!
        FirebaseFirestore.getInstance().collection(CHEF).document(chefId)
            .update(
                mapOf(
                    BOOK_SETTING_TYPE to type,
                    BOOK_SETTING_CALENDAR_DEFAULT to calendarDefault,
                    BOOK_SETTING_USER_SPACE to userSpace,
                    BOOK_SETTING_CHEF_SPACE to chefSpace
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun getMenu(id: String): Result<Menu> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection(MENU)
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Menu::class.java)
                    continuation.resume(Result.Success(data))
                } else {
                    Timber.d("No such document")
                }
            }
            .addOnFailureListener { exception ->
                Timber.d(exception, "get failed with ")
            }
    }

    override suspend fun setReview(menuId: String, review: Review) {
        val reviewId = FirebaseFirestore.getInstance().collection(REVIEW).document().id
        FirebaseFirestore.getInstance().collection(MENU).document(menuId)
            .collection(REVIEW).document(reviewId)
            .set(review)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun setRoom(
        userId: String,
        chefId: String,
        useName: String,
        chefName: String,
        userAvatar: String,
        chefAvatar: String,
    ): Result<String> = suspendCoroutine { continuation ->
        val id = FirebaseFirestore.getInstance().collection(ROOM).document().id
        val attendees = listOf(userId, chefId)
        val room = Room(id, attendees, useName, userAvatar, chefName, chefAvatar)
        FirebaseFirestore.getInstance().collection(ROOM).document(id)
            .set(room)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
                continuation.resume(Result.Success(id))
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun getRoom(userId: String, chefId: String): Result<String> =
        suspendCoroutine { continuation ->
            val attendanceList = listOf(userId, chefId)
            FirebaseFirestore.getInstance().collection(ROOM)
                .whereEqualTo(ATTENDANCE, attendanceList)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        for (doc in value.documents) {
                            val item = doc.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Room::class.java)
                            continuation.resume(Result.Success(data.id))
                        }
                    } else {
                        Timber.d("No such document")
                        continuation.resume(Result.Success(""))
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override fun getLiveRoomList(nowId: String): MutableLiveData<List<Room>> {
        val dataList = mutableListOf<Room>()
        val liveData = MutableLiveData<List<Room>>()

        FirebaseFirestore.getInstance().collection(ROOM)
            .whereArrayContains(ATTENDANCE, nowId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.tag("notification").w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                if (value != null) {
                    dataList.clear()
                    for (document in value.documents) {
                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Room::class.java)
                        if (data.lastMsg != null) {
                            dataList.add(data)
                        }
                        if (value.documents.indexOf(document) == value.documents.lastIndex) {
                            liveData.value = dataList
                        }
                    }
                }
            }
        return liveData
    }

    override fun getLiveChat(roomId: String): MutableLiveData<List<Chat>> {
        val chatList = mutableListOf<Chat>()
        val liveData = MutableLiveData<List<Chat>>()
        FirebaseFirestore.getInstance().collection(ROOM)
            .document(roomId)
            .collection(CHAT)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.tag("notification").w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                if (value != null) {
                    chatList.clear()
                    for (document in value.documents) {
                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Chat::class.java)
                        chatList.add(data)
                        if (value.documents.indexOf(document) == value.documents.lastIndex) {
                            chatList.sortBy { it.time }
                            liveData.value = chatList
                        }
                    }
                }
            }
        return liveData
    }

    override suspend fun updateRoom(roomId: String, msg: String, nowId: String, time: Long) {
        FirebaseFirestore.getInstance().collection(ROOM).document(roomId)
            .update(
                mapOf(
                    LAST_MSG to msg,
                    LAST_DATA_TYPE to MsgType.String.index,
                    TIME to time
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun setChat(roomId: String, msg: String, nowId: String, time: Long) {
        val id = FirebaseFirestore.getInstance().collection(CHAT).document().id
        val chat = Chat(msg, MsgType.String.index, nowId, time)
        FirebaseFirestore.getInstance().collection(ROOM).document(roomId)
            .collection(CHAT).document(id)
            .set(chat)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun setMenu(
        menuName: String,
        menuIntro: String,
        perPrice: Int,
        images: List<String>,
        discountList: List<Discount>,
        dishList: List<Dish>,
        tagList: List<String>,
        openBoolean: Boolean,
    ) {
        val id = FirebaseFirestore.getInstance().collection(MENU).document().id
        val chefId = UserManger.chef?.id!!
        val chefName = UserManger.chef?.profileInfo?.name!!
        val avatar = UserManger.chef?.profileInfo?.avatar!!

        val menu = Menu(
            id,
            chefId,
            menuName,
            chefName,
            avatar,
            menuIntro,
            perPrice,
            images,
            discountList,
            dishList,
            tagList = tagList,
            open = openBoolean
        )

        FirebaseFirestore.getInstance().collection(MENU).document(id)
            .set(menu)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun updateLikeList(newList: List<String>) {
        val userId = UserManger.user?.userId!!
        FirebaseFirestore.getInstance().collection(USER).document(userId)
            .update(
                mapOf(
                    LIKE_LIST to newList,
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun getMenuReviewList(menuId: String): Result<List<Review>> =
        suspendCoroutine { continuation ->
            val dataList = mutableListOf<Review>()
            FirebaseFirestore.getInstance().collection(MENU).document(menuId)
                .collection(REVIEW)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        for (doc in value.documents) {
                            val item = doc.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Review::class.java)
                            dataList.add(data)
                            if (value.documents.indexOf(doc) == value.documents.lastIndex) {
                                continuation.resume(Result.Success(dataList))
                            }
                        }
                    } else {
                        Timber.d("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override fun getLiveChefDateSetting(chefId: String): MutableLiveData<List<DateStatus>> {
        val dateStatus = mutableListOf<DateStatus>()
        val liveData = MutableLiveData<List<DateStatus>>()
        FirebaseFirestore.getInstance().collection(CHEF)
            .document(chefId).collection(DATE_SETTING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                if (value != null) {
                    for (i in value.documents) {
                        val item = i.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, DateStatus::class.java)
                        dateStatus.add(data)
                        if (value.documents.indexOf(i) == value.documents.lastIndex) {
                            liveData.value = dateStatus
                        }
                    }
                }
            }
        return liveData
    }

    override suspend fun setDateSetting(date: Long, status: Int) {
        val chefId = UserManger.chef?.id!!
        FirebaseFirestore.getInstance().collection(CHEF).document(chefId)
            .collection(DATE_SETTING).document(date.toString())
            .set(
                mapOf(
                    DATE to date,
                    STATUS to status
                )
            )
            .addOnSuccessListener {
                Timber.d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun getChefMenuList(chefId: String): Result<List<Menu>> =
        suspendCoroutine { continuation ->
            val menuList = mutableListOf<Menu>()
            FirebaseFirestore.getInstance().collection(MENU)
                .whereEqualTo(CHEF_ID, chefId)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        for (doc in value.documents) {
                            val item = doc.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Menu::class.java)
                            menuList.add(data)
                            if (value.documents.indexOf(doc) == value.documents.size - 1) {
                                continuation.resume(Result.Success(menuList))
                            }
                        }
                    } else {
                        Timber.d("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override fun getLiveOrder(field: String, value: String): MutableLiveData<List<Order>> {
        val orderList = mutableListOf<Order>()
        val liveData = MutableLiveData<List<Order>>()
        FirebaseFirestore.getInstance().collection(ORDER)
            .whereEqualTo(field, value)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Timber.w(e, "Listen failed.")
                    return@addSnapshotListener
                }
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Order::class.java)
                    orderList.add(data)
                    if (value.documents.indexOf(doc) == value.documents.lastIndex) {
                        liveData.value = orderList
                    }
                }
            }
        return liveData
    }

    override suspend fun getTransaction(): Result<List<Transaction>> =
        suspendCoroutine { continuation ->
            val chefId = UserManger.user?.chefId!!
            val dataList = mutableListOf<Transaction>()
            FirebaseFirestore.getInstance().collection(TRANSACTION)
                .whereEqualTo(CHEF_ID, chefId)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        for (doc in value.documents) {
                            val item = doc.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Transaction::class.java)
                            dataList.add(data)
                            if (value.documents.indexOf(doc) == value.documents.lastIndex) {
                                continuation.resume(Result.Success(dataList))
                            }
                        }
                    } else {
                        Timber.d("No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.d(exception, "get failed with ")
                }
        }

    override suspend fun setTransaction(transaction: Transaction) {
        FirebaseFirestore.getInstance().collection(TRANSACTION).document(transaction.id)
            .set(transaction)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }
}
