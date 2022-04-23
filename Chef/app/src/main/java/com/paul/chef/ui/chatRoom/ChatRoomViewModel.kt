package com.paul.chef.ui.chatRoom

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.MsgType
import com.paul.chef.UserManger
import com.paul.chef.data.Chat
import java.util.*


class ChatRoomViewModel (application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()



//    data class Msg(
//        val message: String,
//        val dataType: Int,  //0String, 1image,
//        val senderId: String, //userId,chefId
//        val time: Long, //用來排序
//    )

    fun sendMsg(roomId:String, msg:String, nowId:String){
        val id = db.collection("Chat").document().id
        val time = Calendar.getInstance().timeInMillis
        val chat = Chat(msg, MsgType.String.index,nowId,time)


        //set firebase資料
        db.collection("Room").document(roomId)
            .collection("Chat").document(id)
            .set(chat)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")

            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }



//        val id: String,
//        val lastMsg: String?=null,
//        val lastDataType: Int?=null,  //0String, 1image,
//        val attendance: List<String>, //userId,chefId
////    val chat:List<Chat>? = null,
//        val lastTime: Long?=null, //用來排序

        //
        db.collection("Room").document(roomId)
            .update(mapOf(
                "lastMsg" to msg,
                "lastDataType" to MsgType.String.index,
                "lastTime" to time
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")

            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }










    }

}