package com.paul.chef.ui.orderDetail

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()

    fun changeStatus(orderId:String, status:Int){
        //set firebase資料
        db.collection("Order").document(orderId)
            .update(mapOf(
                "status" to status,
            ))
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }

}