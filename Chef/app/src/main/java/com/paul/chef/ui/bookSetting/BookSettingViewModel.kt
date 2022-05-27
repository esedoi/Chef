package com.paul.chef.ui.bookSetting

import androidx.lifecycle.*
import com.paul.chef.LoadApiStatus
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.BookSetting
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class BookSettingViewModel(private val repository: ChefRepository) : ViewModel() {


    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {

        viewModelScope.launch {
            when (val result = UserManger.chef?.let { repository.getChef(it.id) }) {
                is Result.Success -> {
                    if (result.data.bookSetting != null) {
                        _bookSetting.value = result.data.bookSetting?:return@launch
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
                null -> {

                }
            }
        }
    }

    fun setting(type: Int, calendarDefault: Int, chefSpace: ChefSpace?, userSpace: UserSpace?) {
        viewModelScope.launch {
            repository.updateBookSetting(type, calendarDefault, chefSpace, userSpace)
        }
    }
}
