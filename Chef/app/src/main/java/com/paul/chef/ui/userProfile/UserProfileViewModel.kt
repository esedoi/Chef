package com.paul.chef.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.LoadApiStatus
import com.paul.chef.data.User
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class UserProfileViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private var _getChefDone = MutableLiveData<Boolean>()
    val getChefDone: LiveData<Boolean>
        get() = _getChefDone

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun createChef(user: User) {
        viewModelScope.launch {
            when (val result = repository.createChef(user)) {
                is Result.Success -> {
                    _chefId.value = result.data ?: return@launch
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

    fun getChef(chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getChef(chefId, false)) {
                is Result.Success -> {
                    _getChefDone.value = true
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
