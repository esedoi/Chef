package com.paul.chef.ui.chefEdit

import androidx.lifecycle.*
import com.paul.chef.LoadApiStatus
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class ChefEditViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _userId = MutableLiveData<String>()
    val userId: LiveData<String>
        get() = _userId

    private var _getUserDone = MutableLiveData<User>()
    val getUserDone: LiveData<User>
        get() = _getUserDone

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getUser(userId: String) {
        viewModelScope.launch {
            when (val result = repository.getUser(userId)) {
                is Result.Success -> {
                    _getUserDone.value = result.data ?: return@launch
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

    fun saveChef(profileInfo: ProfileInfo, userId: String, chefId: String) {
        viewModelScope.launch {
            repository.updateProfile(profileInfo, userId, chefId)
        }
    }

    fun createUser(profileInfo: ProfileInfo) {
        viewModelScope.launch {
            when (val result = repository.setUser(profileInfo)) {
                is Result.Success -> {
                    _userId.value = result.data ?: return@launch
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
