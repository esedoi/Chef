package com.paul.chef.data.source.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.MsgType
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefDataSource
import com.paul.chef.data.source.Result
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object ChefFirebaseDataSource : ChefDataSource {

    override suspend fun getMenuList(): Result<List<Menu>> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance().collection("Menu")
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
                    } else {
                        Log.d("firebaseDataSource", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override fun getLiveMenuList(): MutableLiveData<List<Menu>> {
        val dataList = mutableListOf<Menu>()
        val liveData = MutableLiveData<List<Menu>>()
        FirebaseFirestore.getInstance().collection("Menu")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
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
            val id = FirebaseFirestore.getInstance().collection("User").document().id
            val user = User(id, profileInfo)

            FirebaseFirestore.getInstance().collection("User").document(id)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("firebaseDataSource",
                        "DocumentSnapshot added with ID: $documentReference")
                    continuation.resume(Result.Success(id))
                }
                .addOnFailureListener { e ->
                    Log.w("firebaseDataSource", "Error adding document", e)
                }
        }

    override suspend fun getUserByEmail(email: String): Result<User?> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection("User")
                .whereEqualTo("profileInfo.email", email)
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
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override suspend fun getUser(userId: String): Result<User> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection("User")
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
                    Log.d("firebaseDataSource", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("firebaseDataSource", "get failed with ", exception)
            }
    }

    override fun getLiveUser(): MutableLiveData<User> {
        val userId = UserManger.user?.userId!!

        val liveData = MutableLiveData<User>()
        FirebaseFirestore.getInstance().collection("User")
            .document(userId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                val item = value?.data
                val json = Gson().toJson(item)
                val data = Gson().fromJson(json, User::class.java)
                Log.d("firebaseDatasource", "liveUser = $data")
                UserManger.user = data
                liveData.value = data
            }
        return liveData
    }

    override suspend fun getChef(id: String): Result<Chef> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection("Chef")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    UserManger.chef = data
                    continuation.resume(Result.Success(data))
                } else {
                    Log.d("firebaseDataSource", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("firebaseDataSource", "get failed with ", exception)
            }
    }

    override fun getLiveChef(id: String): MutableLiveData<Chef> {
        val liveData = MutableLiveData<Chef>()
        FirebaseFirestore.getInstance().collection("Chef")
            .document(id)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                val item = value?.data
                val json = Gson().toJson(item)
                val data = Gson().fromJson(json, Chef::class.java)
                UserManger.chef = data
                liveData.value = data
            }
        return liveData
    }

    override suspend fun getChefIdList(settingType: List<Int>): Result<List<String>> =
        suspendCoroutine { continuation ->
            val chefList = mutableListOf<String>()
            FirebaseFirestore.getInstance().collection("Chef")
                .whereIn("bookSetting.type", settingType)
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
                        Log.d("firebaseDataSource", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override suspend fun blockMenu(blockMenuList: List<String>, userId: String) {
        FirebaseFirestore.getInstance().collection("User").document(userId)
            .update(
                mapOf(
                    "blockMenuList" to blockMenuList,
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun blockReview(blockReviewList: List<String>, userId: String) {
        FirebaseFirestore.getInstance().collection("User").document(userId)
            .update(
                mapOf(
                    "blockReviewList" to blockReviewList,
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun createChef(user: User): Result<String> = suspendCoroutine { continuation ->
        val id = FirebaseFirestore.getInstance().collection("Chef").document().id
        val profileInfo = user.profileInfo
        val userId = UserManger.user?.userId
        if (profileInfo != null && userId != null) {
            val temp = Chef(id, profileInfo)

            FirebaseFirestore.getInstance().collection("Chef").document(id)
                .set(temp)
                .addOnSuccessListener { documentReference ->
                    Log.d("firebaseDataSource",
                        "DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Log.w("firebaseDataSource", "Error adding document", e)
                }

            FirebaseFirestore.getInstance().collection("User").document(userId)
                .update(
                    mapOf(
                        "chefId" to id
                    )
                )
                .addOnSuccessListener { documentReference ->
                    Log.d("firebaseDataSource",
                        "DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Log.w("firebaseDataSource", "Error adding document", e)
                }
        }
        continuation.resume(Result.Success(id))
    }

    override suspend fun updateProfile(profileInfo: ProfileInfo, userId: String, chefId: String) {
        val info = ProfileInfo(
            profileInfo.name,
            profileInfo.email,
            profileInfo.avatar,
            profileInfo.introduce
        )

        FirebaseFirestore.getInstance().collection("Chef").document(chefId)
            .update(
                mapOf(
                    "profileInfo" to info,
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

        FirebaseFirestore.getInstance().collection("User").document(userId)
            .update(
                mapOf(
                    "profileInfo" to info,
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun updateAddress(addressList: List<Address>) {
        val userId = UserManger.user?.userId!!
        FirebaseFirestore.getInstance().collection("User")
            .document(userId)
            .update(
                mapOf(
                    "address" to addressList,
                )
            )
            .addOnSuccessListener {
                Log.d(
                    "notification",
                    "DocumentSnapshot successfully updated!"
                )
            }
            .addOnFailureListener { exception ->
                Log.d("firebaseDataSource", "get failed with ", exception)
            }
    }

    override suspend fun setOrder(order: Order): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance().collection("Order").document(order.id)
                .set(order)
                .addOnSuccessListener { documentReference ->
                    Log.d("firebaseDataSource",
                        "DocumentSnapshot added with ID: $documentReference")
                    continuation.resume(Result.Success(true))
                }
                .addOnFailureListener { e ->
                    Log.w("firebaseDataSource", "Error adding document", e)
                }
        }

    override suspend fun updateOrderStatus(status: Int, orderId: String) {
        FirebaseFirestore.getInstance().collection("Order").document(orderId)
            .update(
                mapOf(
                    "status" to status
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
            }
    }

    override suspend fun updateChefReview(
        chefId: String,
        newChefRating: Float,
        newChefRatingNumber: Int,
    ) {
        FirebaseFirestore.getInstance().collection("Chef").document(chefId)
            .update(
                mapOf(
                    "reviewRating" to newChefRating,
                    "reviewNumber" to newChefRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
            }
    }

    override suspend fun updateMenuReview(
        menuId: String,
        newMenuRating: Float,
        newMenuRatingNumber: Int,
    ) {
        FirebaseFirestore.getInstance().collection("Menu").document(menuId)
            .update(
                mapOf(
                    "reviewRating" to newMenuRating,
                    "reviewNumber" to newMenuRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
            }
    }

    override suspend fun updateBookSetting(
        type: Int,
        calendarDefault: Int,
        chefSpace: ChefSpace?,
        userSpace: UserSpace?,
    ) {
        val chefId = UserManger.user?.chefId!!
        FirebaseFirestore.getInstance().collection("Chef").document(chefId)
            .update(
                mapOf(
                    "bookSetting.type" to type,
                    "bookSetting.calendarDefault" to calendarDefault,
                    "bookSetting.userSpace" to userSpace,
                    "bookSetting.chefSpace" to chefSpace
                )
            )
            .addOnSuccessListener {
                Log.d(
                    "notification",
                    "DocumentSnapshot successfully updated!"
                )
            }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }

    override suspend fun getMenu(id: String): Result<Menu> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance().collection("Menu")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("pickerViewModel", "DocumentSnapshot data: ${document.data}")
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Menu::class.java)
                    continuation.resume(Result.Success(data))
                } else {
                    Log.d("firebaseDataSource", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("firebaseDataSource", "get failed with ", exception)
            }
    }

    override suspend fun setReview(menuId: String, review: Review) {
        val reviewId = FirebaseFirestore.getInstance().collection("Review").document().id
        FirebaseFirestore.getInstance().collection("Menu").document(menuId)
            .collection("Review").document(reviewId)
            .set(review)
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
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
        val id = FirebaseFirestore.getInstance().collection("Room").document().id
        val attendees = listOf(userId, chefId)
        val room = Room(id, attendees, useName, userAvatar, chefName, chefAvatar)
        FirebaseFirestore.getInstance().collection("Room").document(id)
            .set(room)
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
                continuation.resume(Result.Success(id))
            }
            .addOnFailureListener { e ->
                Timber.w(e, "Error adding document")
            }
    }

    override suspend fun getRoom(userId: String, chefId: String): Result<String> =
        suspendCoroutine { continuation ->
            val attendanceList = listOf(userId, chefId)
            FirebaseFirestore.getInstance().collection("Room")
                .whereEqualTo("attendance", attendanceList)
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

        FirebaseFirestore.getInstance().collection("Room")
            .whereArrayContains("attendance", nowId)
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
        FirebaseFirestore.getInstance().collection("Room")
            .document(roomId)
            .collection("Chat")
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
                        if(value.documents.indexOf(document)==value.documents.lastIndex){
                            chatList.sortBy { it.time }
                            liveData.value = chatList
                        }
                    }
                }
            }
        return  liveData
    }

    override suspend fun updateRoom(roomId: String, msg: String, nowId: String, time: Long) {
        FirebaseFirestore.getInstance().collection("Room").document(roomId)
            .update(
                mapOf(
                    "lastMsg" to msg,
                    "lastDataType" to MsgType.String.index,
                    "time" to time
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun setChat(roomId: String, msg: String, nowId: String, time: Long) {
        val id = FirebaseFirestore.getInstance().collection("Chat").document().id
        val chat = Chat(msg, MsgType.String.index, nowId, time)
        FirebaseFirestore.getInstance().collection("Room").document(roomId)
            .collection("Chat").document(id)
            .set(chat)
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
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
        val id = FirebaseFirestore.getInstance().collection("Menu").document().id
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

        FirebaseFirestore.getInstance().collection("Menu").document(id)
            .set(menu)
            .addOnSuccessListener { documentReference ->
                Log.d("firebaseDataSource", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
            }
    }

    override suspend fun updateLikeList(newList: List<String>) {
        val userId = UserManger.user?.userId!!
        FirebaseFirestore.getInstance().collection("User").document(userId)
            .update(
                mapOf(
                    "likeList" to newList,
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun getMenuReviewList(menuId: String): Result<List<Review>> =
        suspendCoroutine { continuation ->
            val dataList = mutableListOf<Review>()
            FirebaseFirestore.getInstance().collection("Menu").document(menuId)
                .collection("Review")
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
                        Log.d("firebaseDataSource", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override fun getLiveChefDateSetting(chefId: String): MutableLiveData<List<DateStatus>> {
        val dateStatus = mutableListOf<DateStatus>()
        val liveData = MutableLiveData<List<DateStatus>>()
        FirebaseFirestore.getInstance().collection("Chef")
            .document(chefId).collection("dateSetting")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
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
        FirebaseFirestore.getInstance().collection("Chef").document(chefId)
            .collection("dateSetting").document(date.toString())
            .set(
                mapOf(
                    "date" to date,
                    "status" to status
                )
            )
            .addOnSuccessListener {
                Log.d("notification", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
    }

    override suspend fun getChefMenuList(chefId: String): Result<List<Menu>> =
        suspendCoroutine { continuation ->
            val menuList = mutableListOf<Menu>()
            FirebaseFirestore.getInstance().collection("Menu")
                .whereEqualTo("chefId", chefId)
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
                        Log.d("firebaseDataSource", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override fun getLiveOrder(field: String, value: String): MutableLiveData<List<Order>> {
        val orderList = mutableListOf<Order>()
        val liveData = MutableLiveData<List<Order>>()
        FirebaseFirestore.getInstance().collection("Order")
            .whereEqualTo(field, value)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
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
            FirebaseFirestore.getInstance().collection("Transaction")
                .whereEqualTo("chefId", chefId)
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
                        Log.d("firebaseDataSource", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebaseDataSource", "get failed with ", exception)
                }
        }

    override suspend fun setTransaction(transaction: Transaction) {
        FirebaseFirestore.getInstance().collection("Transaction").document(transaction.id)
            .set(transaction)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "firebaseDataSource",
                    "DocumentSnapshot added with ID: $documentReference"
                )
            }
            .addOnFailureListener { e ->
                Log.w("firebaseDataSource", "Error adding document", e)
            }
    }
}
