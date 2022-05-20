package com.paul.chef.ui.chefEdit


import androidx.lifecycle.*
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



    fun getUser(userId: String) {

        viewModelScope.launch {
           when(val result =  repository.getUser(userId)){
                is Result.Success->{
                    _getUserDone.value = result.data!!
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
                    _userId.value = result.data!!
                }
            }
        }

    }

}


