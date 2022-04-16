package com.paul.chef.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.ChefMenu

class MenuListViewModel(application: Application) : AndroidViewModel(application){


    val dataList = mutableListOf<ChefMenu>()
    var _menuList = MutableLiveData<List<ChefMenu>>()
    val menuList: LiveData<List<ChefMenu>>
        get() = _menuList
    private val db = FirebaseFirestore.getInstance()

    init {

        db.collection("Menu")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
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


}