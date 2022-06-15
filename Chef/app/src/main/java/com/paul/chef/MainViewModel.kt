package com.paul.chef


import androidx.lifecycle.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private var _newUser = MutableLiveData<Boolean>()
    val newUser: LiveData<Boolean>
        get() = _newUser

    private var _isChef = MutableLiveData<Boolean>()
    val isChef: LiveData<Boolean>
        get() = _isChef

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun getUser(email: String) {
        viewModelScope.launch {

            when (val result = repository.getUserByEmail(email)) {
                is Result.Success -> {
                    _newUser.value = result.data == null
                    if (result.data?.chefId != null) {
                        getChef(result.data.chefId)
                    }
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

    private fun getChef(chefId: String) {
        repository.getLiveChef(chefId, false)

    }

    fun block(userId: String, blockMenuList: List<String>?, blockReviewList: List<String>?) {
        viewModelScope.launch {
            if (blockMenuList != null) {
                repository.blockMenu(blockMenuList, userId)
            }
            if (blockReviewList != null) {
                repository.blockReview(blockReviewList, userId)
            }
        }
    }
}
