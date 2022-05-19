package com.paul.chef.data.source.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.paul.chef.data.source.Result


object ChefFirebaseDataSource : ChefDataSource {


    override suspend fun getMenuList(id: String): Result<List<Menu>> =
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
                        Log.d("pickerViewModel", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("pickerViewModel", "get failed with ", exception)
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
                    Log.d("pickerViewModel", "get failed with ", exception)
                }
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
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }
    }


    override fun getLiveChef(id: String): MutableLiveData<Chef>  {
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
                    Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")

                }
                .addOnFailureListener { e ->
                    Log.w("click", "Error adding document", e)
                }


            FirebaseFirestore.getInstance().collection("User").document(userId)
                .update(
                    mapOf(
                        "chefId" to id
                    )
                )
                .addOnSuccessListener { documentReference ->
                    Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w("click", "Error adding document", e)
                }


        }
        continuation.resume(Result.Success(id))

    }

    override suspend fun updateOrderStatus(status: Int, orderId: String) {
        FirebaseFirestore.getInstance().collection("Order").document(orderId)
            .update(
                mapOf(
                    "status" to status
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")

            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }

    override suspend fun updateChefReview(
        chefId: String,
        newChefRating: Float,
        newChefRatingNumber: Int
    ) {
        FirebaseFirestore.getInstance().collection("Chef").document(chefId)
            .update(
                mapOf(
                    "reviewRating" to newChefRating,
                    "reviewNumber" to newChefRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }

    override suspend fun updateMenuReview(
        menuId: String,
        newMenuRating: Float,
        newMenuRatingNumber: Int
    ) {
        FirebaseFirestore.getInstance().collection("Menu").document(menuId)
            .update(
                mapOf(
                    "reviewRating" to newMenuRating,
                    "reviewNumber" to newMenuRatingNumber
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
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
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }

    }

    override suspend fun setReview(menuId: String, review: Review) {
        val reviewId = FirebaseFirestore.getInstance().collection("Review").document().id
        FirebaseFirestore.getInstance().collection("Menu").document(menuId)
            .collection("Review").document(reviewId)
            .set(review)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }

    override suspend fun setRoom(userId:String, chefId:String,useName:String, chefName:String, userAvatar:String, chefAvatar:String):Result<String> = suspendCoroutine { continuation ->
        val id = FirebaseFirestore.getInstance().collection("Room").document().id
        val attendees = listOf(userId, chefId)
        val room = Room(id,attendees, useName, userAvatar, chefName, chefAvatar)
        FirebaseFirestore.getInstance().collection("Room").document(id)
            .set(room)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                continuation.resume(Result.Success(id))

            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }

    override suspend fun getRoom(userId: String, chefId: String): Result<String> = suspendCoroutine { continuation ->
        val attendanceList = listOf(userId, chefId)
        FirebaseFirestore.getInstance().collection("Room")
            .whereEqualTo("attendance", attendanceList)
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    for (item in value.documents){
                        val item = item.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Room::class.java)
                        continuation.resume(Result.Success(data.id))
                    }
                } else {
                    Log.d("orderdetailviewmodel", "No such document")
                    continuation.resume(Result.Success(""))
                }
            }
            .addOnFailureListener { exception ->
                Log.d("orderdetailviewmodel", "get failed with ", exception)
            }
    }
}