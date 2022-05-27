package com.paul.chef.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paul.chef.UserManger
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Chef
import com.paul.chef.data.DateStatus
import com.paul.chef.data.Order
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.util.ConstValue.CHEF_ID
import java.time.LocalDate

class CalendarViewModel(repository: ChefRepository) : ViewModel() {

    private var _orderList = MutableLiveData<List<LocalDate>>()
    val orderList: LiveData<List<LocalDate>>
        get() = _orderList

    private var _dateSetting = MutableLiveData<List<DateStatus>>()
    val dateSetting: LiveData<List<DateStatus>>
        get() = _dateSetting

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    val chefId = UserManger.chef?.id

    var liveOrderList = MutableLiveData<List<Order>>()
    var liveChef = MutableLiveData<Chef>()

    init {
        if(chefId!=null){
            liveOrderList = repository.getLiveOrder(CHEF_ID, chefId)
            _dateSetting = repository.getLiveChefDateSetting(chefId)
            liveChef = repository.getLiveChef(chefId)
        }
    }

    fun getOrderDateList(orderList: List<Order>) {
        val dateList = mutableListOf<LocalDate>()
        for (order in orderList) {
            val localDate: LocalDate = LocalDate.ofEpochDay(order.date)
            dateList.add(localDate)
            if (orderList.indexOf(order) == orderList.lastIndex) {
                _orderList.value = dateList
            }
        }
    }

    fun getBookSetting(chef: Chef) {
        if (chef.bookSetting != null) {
            _bookSetting.value = chef.bookSetting?:return
        }
    }
}
