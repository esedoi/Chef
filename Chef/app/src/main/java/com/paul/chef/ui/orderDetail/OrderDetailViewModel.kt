package com.paul.chef.ui.orderDetail

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.Room

class OrderDetailViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()


    private var _roomId = MutableLiveData<String>()
    val roomId: LiveData<String>
        get() = _roomId


    fun getRoomId(userId:String, chefId:String){

        val attendanceList = listOf(userId, chefId)
        db.collection("Room")
            .whereEqualTo("attendance", attendanceList)
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    for (item in value.documents){
                        val item = item.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Room::class.java)
                       _roomId.value = data.id
                    }
                } else {
                    Log.d("orderdetailviewmodel", "No such document")
                    _roomId.value = ""
                }
            }
            .addOnFailureListener { exception ->
                Log.d("orderdetailviewmodel", "get failed with ", exception)
            }

    }



    fun createRoom(userId:String, chefId:String, useName:String, chefName:String, userAvatar:String, chefAvatar:String){

        val id = db.collection("Room").document().id
        val attendees = listOf(userId, chefId)
        val room = Room(id,attendees, useName, userAvatar, chefName, chefAvatar)
        db.collection("Room").document(id)
            .set(room)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                _roomId.value = id
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }







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