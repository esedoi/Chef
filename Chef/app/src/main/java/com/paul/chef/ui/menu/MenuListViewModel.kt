package com.paul.chef.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.BookSettingType
import com.paul.chef.BookType
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu
import com.paul.chef.data.User

class MenuListViewModel(application: Application) : AndroidViewModel(application){


    private val dataList = mutableListOf<Menu>()
    private var _menuList = MutableLiveData<List<Menu>>()
    val menuList: LiveData<List<Menu>>
        get() = _menuList


    private var _likeIdList = MutableLiveData<List<String>>()
    val likeIdList: LiveData<List<String>>
        get() = _likeIdList

    private var _likeList = MutableLiveData<List<Menu>>()
    val likeList: LiveData<List<Menu>>
        get() = _likeList

    val chefList  = mutableListOf<String>()
    private val db = FirebaseFirestore.getInstance()

    val userId = UserManger.user?.userId!!

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
                    val data = Gson().fromJson(json, Menu::class.java)
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
                        val data = Gson().fromJson(json, Menu::class.java)
                        dataList.add(data)
                        Log.d("likeviewmodel", "item=$item")
                    }
                    _likeList.value = dataList
                }
        }

    }

    fun getChefId(bookType: Int){
        Log.d("menuListViewmodel", "getChefId")
        val settingType = mutableListOf<Int>()
        settingType.add(BookSettingType.AcceptAll.index)
        if(bookType ==BookType.UserSpace.index){
            settingType.add(BookSettingType.OnlyUserSpace.index)
        }else{
            settingType.add(BookSettingType.OnlyChefSpace.index)
        }
        db.collection("Chef")
            .whereIn("bookSetting.type", settingType)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                   chefList.add(data.id)
                    if(value.documents.indexOf(doc)==value.documents.size-1){
                        getFilterMenuList(chefList)
                    }
                }
            }
    }

    fun getFilterMenuList(chefIdList:List<String>){
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
                    val data = Gson().fromJson(json, Menu::class.java)
                    dataList.add(data)

                    if(value.documents.indexOf(doc)==value.documents.size-1){
                        val  filterList = dataList.filter {
                            chefIdList.contains(it.chefId)
                        }
                        _menuList.value = filterList
                        chefList.clear()

                    }
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