package com.paul.chef.ui.chatRoom


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.data.Chat
import com.paul.chef.data.source.ChefRepository
import java.util.*
import kotlinx.coroutines.launch

class ChatRoomViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _chatList = MutableLiveData<List<Chat>>()
    val chatList: LiveData<List<Chat>>
        get() = _chatList

    fun sendMsg(roomId: String, msg: String, nowId: String) {
        val time = Calendar.getInstance().timeInMillis

        viewModelScope.launch {
            repository.setChat(roomId, msg, nowId, time)
            repository.updateRoom(roomId, msg, nowId, time)
        }
    }

    fun getLiveChat(roomId: String) {
        _chatList = repository.getLiveChat(roomId)
    }
}
