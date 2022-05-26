package com.paul.chef

import android.util.Log
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


    fun getUser(email: String) {
        viewModelScope.launch {
            val result = repository.getUserByEmail(email)
            Log.d("result", "result=$result")
            when (result) {
                is Result.Success -> {
                    _newUser.value = result.data == null
                    if (result.data?.chefId != null) {
                        getChef(result.data.chefId)
                    }
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()

            }
        }
    }

    private fun getChef(chefId: String) {
        repository.getLiveChef(chefId)

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
