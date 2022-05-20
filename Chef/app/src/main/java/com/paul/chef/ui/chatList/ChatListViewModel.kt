package com.paul.chef.ui.chatList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.Mode
import com.paul.chef.UserManger
import com.paul.chef.data.Room
import com.paul.chef.data.source.ChefRepository


class ChatListViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _roomList = MutableLiveData<List<Room>>()
    val roomList: LiveData<List<Room>>
        get() = _roomList

    val mode = UserManger.tempMode

    init {

        val nowId = when (mode) {
            Mode.USER.index -> {
                UserManger.user?.userId!!
            }
            Mode.CHEF.index -> {
                UserManger.chef?.id!!
            }
            else -> {
                ""
            }
        }

        _roomList = repository.getLiveRoomList(nowId)

    }
}