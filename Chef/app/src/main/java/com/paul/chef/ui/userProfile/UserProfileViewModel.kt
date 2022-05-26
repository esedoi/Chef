package com.paul.chef.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun createChef(user: User) {
        viewModelScope.launch {
            when (val result = repository.createChef(user)) {
                is Result.Success -> {
                    _chefId.value = result.data!!
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }

    fun getChef(chefId: String) {
        viewModelScope.launch {
            when (repository.getChef(chefId)) {
                is Result.Success -> {
                    _getChefDone.value = true
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }
}
