package com.paul.chef.ui.orderDetail

import androidx.lifecycle.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _roomId = MutableLiveData<String>()
    val roomId: LiveData<String>
        get() = _roomId

    fun getRoomId(userId: String, chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getRoom(userId, chefId)) {
                is Result.Success -> _roomId.value = result.data!!
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }

    fun createRoom(
        userId: String,
        chefId: String,
        useName: String,
        chefName: String,
        userAvatar: String,
        chefAvatar: String
    ) {
        viewModelScope.launch {
            val result =
                repository.setRoom(userId, chefId, useName, chefName, userAvatar, chefAvatar)
            when (result) {
                is Result.Success -> {
                    _roomId.value = result.data!!
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }

    fun changeStatus(orderId: String, status: Int) {
        viewModelScope.launch {
            repository.updateOrderStatus(status, orderId)
        }
    }
}
