package com.paul.chef.ui.calendarSetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.data.DateStatus
import com.paul.chef.data.source.ChefRepository
import java.time.LocalDate
import kotlinx.coroutines.launch

class CalendarSettingViewModel(private val repository: ChefRepository) : ViewModel() {



    fun settingDate(
        status: Int,
        selectDates: List<LocalDate>,
    ) {
        val date = mutableListOf<DateStatus>()
        for (i in selectDates) {
            val long = i.toEpochDay()
            val dateStatus = DateStatus(status, long)
            date.add(dateStatus)
        }

        viewModelScope.launch {
            for (i in date) {
                repository.setDateSetting(i.date, i.status)
            }
        }
    }
}
