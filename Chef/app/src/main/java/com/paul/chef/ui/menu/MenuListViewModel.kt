package com.paul.chef.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.User

class MenuListViewModel(application: Application) : AndroidViewModel(application){


    private val dataList = mutableListOf<ChefMenu>()
    private var _menuList = MutableLiveData<List<ChefMenu>>()
    val menuList: LiveData<List<ChefMenu>>
        get() = _menuList


    private var _likeIdList = MutableLiveData<List<String>>()
    val likeIdList: LiveData<List<String>>
        get() = _likeIdList

    private var _likeList = MutableLiveData<List<ChefMenu>>()
    val likeList: LiveData<List<ChefMenu>>
        get() = _likeList
    private val db = FirebaseFirestore.getInstance()

    val userId = UserManger.user.userId!!

    init {

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


        db.collection("Menu")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                dataList.clear()
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, ChefMenu::class.java)
                    dataList.add(data)
                    Log.d("menufragment", "item=$item")
                }
                _menuList.value = dataList
            }
    }

    fun getLikeList(newList:List<String>){
        if(newList.isEmpty()){
            _likeList.value = emptyList()
        }else{
            db.collection("Menu")
                .whereIn("id",newList )
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.w("notification", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    dataList.clear()
                    for (doc in value!!.documents) {
                        val item = doc.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, ChefMenu::class.java)
                        dataList.add(data)
                        Log.d("likeviewmodel", "item=$item")
                    }
                    _likeList.value = dataList
                }
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