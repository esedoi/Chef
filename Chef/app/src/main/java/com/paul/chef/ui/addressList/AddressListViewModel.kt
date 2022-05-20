package com.paul.chef.ui.addressList


import android.util.Log
import androidx.lifecycle.*
import com.paul.chef.UserManger
import com.paul.chef.data.Address
import com.paul.chef.data.User
import com.paul.chef.data.source.ChefRepository
import kotlinx.coroutines.launch


class AddressListViewModel(private val repository: ChefRepository) : ViewModel() {


    private var _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>>
        get() = _addressList

    private var _lastSelection = MutableLiveData<Int>()
    val lastSelection: LiveData<Int>
        get() = _lastSelection


    val userId = UserManger.user?.userId

    var liveUser = MutableLiveData<User>()

    init {
        if (userId != null) {
            liveUser = repository.getLiveUser()
        }
    }

    fun getAddress(user: User) {
        _addressList.value = user.address ?: emptyList()
    }

    fun updateAddress(addressList: List<Address>) {
        viewModelScope.launch {
            repository.updateAddress(addressList)
        }
    }


    fun deleteAddress(item: Address) {
        Log.d("addressListViewModel", "item=$item")
    }
}