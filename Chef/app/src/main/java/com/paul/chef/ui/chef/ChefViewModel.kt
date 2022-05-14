package com.paul.chef.ui.chef

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu
import com.paul.chef.data.Review

class ChefViewModel(application: Application) : AndroidViewModel(application) {


    private val db = FirebaseFirestore.getInstance()


    private var _chefInfo = MutableLiveData<Chef>()
    val chefInfo: LiveData<Chef>
        get() = _chefInfo

    private val dataList = mutableListOf<Review>()

    private var _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList


    private val menuIdList = mutableListOf<String>()


    private val menuList = mutableListOf<Menu>()
    private var _liveMenu = MutableLiveData<List<Menu>>()
    val liveMenu: LiveData<List<Menu>>
        get() = _liveMenu




        //
        fun getChef(chefId:String) {


            Log.d("chefviewmodel", "UserManger.chef${UserManger.chef}")

            db.collection("Chef")
                .whereEqualTo("id", chefId)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.w("notification", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    for (doc in value!!.documents) {
                        val item = doc.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Chef::class.java)
                        _chefInfo.value = data

                    }
                }

            dataList.clear()
            menuIdList.clear()
            menuList.clear()

            db.collection("Menu")
                .whereEqualTo("chefId", chefId)
                .get()
                .addOnSuccessListener { value ->
                    if (value.documents.isNotEmpty()) {
                        dataList.clear()
                        for (item in value.documents) {
                            val item = item.data
                            val json = Gson().toJson(item)
                            val data = Gson().fromJson(json, Menu::class.java)
                            menuIdList.add(data.id)
                            menuList.add(data)
                        }
                        _liveMenu.value = menuList
                        Log.d("chefviewmodel", "menuidlist=$menuIdList")


                        dataList.clear()
                        for (i in menuIdList) {
                            var menuId = i
                            db.collection("Menu").document(menuId)
                                .collection("Review")
                                .get()
                                .addOnSuccessListener { value ->
                                    if (value.documents.isNotEmpty()) {
                                        for (item in value.documents) {
                                            val item = item.data
                                            val json = Gson().toJson(item)
                                            val data = Gson().fromJson(json, Review::class.java)
                                            dataList.add(data)
                                        }
                                        Log.d("chefviewmodel", "+++++++datalist=$dataList")
                                        _reviewList.value = dataList
                                    } else {
                                        Log.d("chefviewmodel", "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("chefviewmodel", "get failed with ", exception)
                                }
                        }

                    } else {
                        Log.d("orderdetailviewmodel", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("orderdetailviewmodel", "get failed with ", exception)
                }


        }
        //


}
