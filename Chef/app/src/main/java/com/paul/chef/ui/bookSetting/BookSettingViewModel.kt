package com.paul.chef.ui.bookSetting

import androidx.lifecycle.*
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.BookSetting
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class BookSettingViewModel(private val repository: ChefRepository) : ViewModel() {

    val chefId = UserManger.user?.chefId!!

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    init {

        viewModelScope.launch {
            when (val result = UserManger.chef?.let { repository.getChef(it.id) }) {
                is Result.Success -> {
                    if (result.data.bookSetting != null) {
                        _bookSetting.value = result.data.bookSetting!!
                    }
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
                null -> TODO()
            }
        }
    }

    fun setting(type: Int, calendarDefault: Int, chefSpace: ChefSpace?, userSpace: UserSpace?) {
        viewModelScope.launch {
            repository.updateBookSetting(type, calendarDefault, chefSpace, userSpace)
        }
    }
}
