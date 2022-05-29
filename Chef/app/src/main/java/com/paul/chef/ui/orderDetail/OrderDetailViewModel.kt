package com.paul.chef.ui.orderDetail

import androidx.lifecycle.*
import com.paul.chef.LoadApiStatus
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _roomId = MutableLiveData<String>()
    val roomId: LiveData<String>
        get() = _roomId

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _acceptDone = MutableLiveData<Boolean>()
    val acceptDone: LiveData<Boolean>
        get() = _acceptDone

    fun getRoomId(userId: String, chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getRoom(userId, chefId)) {
                is Result.Success -> _roomId.value = result.data?:return@launch
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                Result.Loading -> {

                }
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
                    _roomId.value = result.data?:return@launch
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                Result.Loading -> {

                }
            }
        }
    }

    fun changeStatus(orderId: String, status: Int) {
        viewModelScope.launch {
            when(val result = repository.updateOrderStatus(status, orderId)){
                is Result.Success->{
                    _acceptDone.value = result.data?:return@launch

                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                Result.Loading -> {

                }
            }
        }
    }
}
