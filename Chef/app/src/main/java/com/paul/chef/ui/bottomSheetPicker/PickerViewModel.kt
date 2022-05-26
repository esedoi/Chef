package com.paul.chef.ui.bottomSheetPicker

import androidx.lifecycle.*
import com.paul.chef.data.BookSetting
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class PickerViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    fun getBookSetting(chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getChef(chefId)) {
                is Result.Success -> {
                    if (result.data.bookSetting != null) {
                        _bookSetting.value = result.data.bookSetting!!
                    }
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }
}
