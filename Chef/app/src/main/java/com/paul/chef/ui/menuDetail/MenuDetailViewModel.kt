package com.paul.chef.ui.menuDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.BookSettingType
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.data.User

class MenuDetailViewModel(application: Application) : AndroidViewModel(application){

//    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()

    private val dataList = mutableListOf<Review>()
    private var _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

    private var _openBoolean = MutableLiveData<Boolean>()
    val openBoolean: LiveData<Boolean>
        get() = _openBoolean

    private var _likeIdList = MutableLiveData<List<String>>()
    val likeIdList: LiveData<List<String>>
        get() = _likeIdList

    val userId = UserManger.user?.userId!!


    init{
        db.collection("User")
            .document(userId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                val item = value?.data
                val json = Gson().toJson(item)
                val data = Gson().fromJson(json, User::class.java)
                if(data.likeList!=null){
                    _likeIdList.value = data.likeList!!
                }
            }
    }




    fun checkOpen(chefId:String){
        db.collection("Chef")
            .document(chefId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("pickerViewModel", "DocumentSnapshot data: ${document.data}")
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    _openBoolean.value = data.bookSetting!=null&&data.bookSetting.type!= BookSettingType.RefuseAll.index

                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }
    }



    fun getReview(menuId:String){

        db.collection("Menu").document(menuId)
            .collection("Review")
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    dataList.clear()
                    for (item in value.documents){
                        val item = item.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Review::class.java)
                       dataList.add(data)
                    }
                } else {
                    Log.d("orderdetailviewmodel", "No such document")
                }
                _reviewList.value = dataList
            }
            .addOnFailureListener { exception ->
                Log.d("orderdetailviewmodel", "get failed with ", exception)
            }
    }

    fun updateLikeList(newList:List<String>){

        db.collection("User").document(userId)
            .update(mapOf(
                "likeList" to newList,
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")

            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }

}