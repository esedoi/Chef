package com.paul.chef.ui.datePicker

import androidx.lifecycle.*
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Chef
import com.paul.chef.data.DateStatus
import com.paul.chef.data.source.ChefRepository

class DatePickerViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _dateSetting = MutableLiveData<List<DateStatus>>()
    val dateSetting: LiveData<List<DateStatus>>
        get() = _dateSetting

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    var liveChef = MutableLiveData<Chef>()

    fun getChefData(chefId: String) {
        _dateSetting = repository.getLiveChefDateSetting(chefId)

        liveChef = repository.getLiveChef(chefId, true)
    }

    fun getBookSetting(chef: Chef) {
        _bookSetting.value = chef.bookSetting!!
    }
}
