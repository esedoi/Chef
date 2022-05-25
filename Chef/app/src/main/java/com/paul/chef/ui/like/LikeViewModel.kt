package com.paul.chef.ui.like

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.UserManger

class LikeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val userId = UserManger.user?.userId!!

//    private var _likeList = MutableLiveData<List<String>>()
//    val likeList: LiveData<List<String>>
//        get() = _likeList

//    private val dataList = mutableListOf<ChefMenu>()
//    private var _menuList = MutableLiveData<List<ChefMenu>>()
//    val menuList: LiveData<List<ChefMenu>>
//        get() = _menuList

//    init {
//
//        db.collection("User")
//            .document(userId)
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("notification", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//                val item = value?.data
//                val json = Gson().toJson(item)
//                val data = Gson().fromJson(json, User::class.java)
//
//                if (data.likeList?.isNotEmpty() == true) {
//                    _likeList.value = data.likeList!!
//                }
//
//            }
//    }

//    fun getMenuList(newList:List<String>){
//        if(newList.isEmpty()){
//            _menuList.value = emptyList()
//        }else{
//            db.collection("Menu")
//                .whereIn("id",newList )
//                .addSnapshotListener { value, e ->
//                    if (e != null) {
//                        Log.w("notification", "Listen failed.", e)
//                        return@addSnapshotListener
//                    }
//                    dataList.clear()
//                    for (doc in value!!.documents) {
//                        val item = doc.data
//                        val json = Gson().toJson(item)
//                        val data = Gson().fromJson(json, ChefMenu::class.java)
//                        dataList.add(data)
//                        Log.d("likeviewmodel", "item=$item")
//                    }
//                    _menuList.value = dataList
//                }
//        }
//
//    }

//    fun updateLikeList(newList:List<String>){
//
//        db.collection("User").document(userId)
//            .update(mapOf(
//                "likeList" to newList,
//            ))
//            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")
//
//            }
//            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
//
//    }
}
