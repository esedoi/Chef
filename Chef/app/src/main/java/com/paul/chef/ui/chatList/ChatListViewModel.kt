package com.paul.chef.ui.chatList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.Mode
import com.paul.chef.UserManger
import com.paul.chef.data.Room


class ChatListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val dateList = mutableListOf<Room>()
    private var _roomList = MutableLiveData<List<Room>>()
    val roomList: LiveData<List<Room>>
        get() = _roomList



    val mode = UserManger.tempMode

    init {

        val nowId = when(mode){
            Mode.USER.index-> {
             UserManger.user?.userId!!
            }
            Mode.CHEF.index-> {
               UserManger.chef?.id!!
            }
            else->{
                ""
            }
        }

        db.collection("Room")
            .whereArrayContains("attendance", nowId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                dateList.clear()
                if (value != null) {
                    for (document in value.documents) {
                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Room::class.java)
                        if (data.lastMsg != null) {
                            dateList.add(data)
                        }
                        if(value.documents.indexOf(document)==value.documents.size-1){
                          _roomList.value = dateList
                        }
                    }
                }
            }
        }
}