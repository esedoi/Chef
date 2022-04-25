package com.paul.chef.ui.chef

import android.app.Application
import android.util.Log
import android.view.Menu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.ChefManger
import com.paul.chef.data.Chef
import com.paul.chef.data.ChefMenu
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

     val chefId = ChefManger().chefId
    private val menuList = mutableListOf<String>()



    init {
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

        db.collection("Menu")
            .whereEqualTo("chefId",chefId )
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    dataList.clear()
                    for (item in value.documents){
                        val item = item.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, ChefMenu::class.java)
                        menuList.add(data.id)
                    }

                    for(i in menuList){
                        var menuId = i

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
                } else {
                    Log.d("orderdetailviewmodel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("orderdetailviewmodel", "get failed with ", exception)
            }

    }
}
