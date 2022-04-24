package com.paul.chef.ui.menuDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.Review
import com.paul.chef.data.Room

class MenuDetailViewModel(application: Application) : AndroidViewModel(application){

//    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()

    private val dataList = mutableListOf<Review>()
    private var _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

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

}