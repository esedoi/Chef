package com.paul.chef.ui.filterSheet

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.Address
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu

class FilterViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()


    fun getMenuList(type:Int, address: Address){



//            db.collection("Chef")
//                .whereEqualTo("bookSetting.type", type)
//                .whereEqualTo("efef",  type)
//                .addSnapshotListener { value, e ->
//                    if (e != null) {
//                        Log.w("notification", "Listen failed.", e)
//                        return@addSnapshotListener
//                    }
//                    dataList.clear()
//                    for (doc in value!!.documents) {
//                        val item = doc.data
//                        val json = Gson().toJson(item)
//                        val data = Gson().fromJson(json, Menu::class.java)
//                        dataList.add(data)
//                        Log.d("menufragment", "item=$item")
//                    }
//                    _menuList.value = dataList
//                }


    }
}