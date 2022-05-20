package com.paul.chef.ui.chatRoom


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.data.source.ChefRepository
import kotlinx.coroutines.launch
import java.util.*


class ChatRoomViewModel (private val repository: ChefRepository) : ViewModel() {


    fun sendMsg(roomId:String, msg:String, nowId:String){

        val time = Calendar.getInstance().timeInMillis

        viewModelScope.launch {
            repository.setChat(roomId, msg, nowId, time)
            repository.updateRoom(roomId, msg, nowId, time)

        }
    }
}