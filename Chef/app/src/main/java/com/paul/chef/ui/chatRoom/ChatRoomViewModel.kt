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


        db.collection("Room").document(roomId)
            .update(mapOf(
                "lastMsg" to msg,
                "lastDataType" to MsgType.String.index,
                "time" to time
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")

            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }










    }

}