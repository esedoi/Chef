package com.paul.chef.ui.orderManage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.util.ConstValue.CHEF_ID
import com.paul.chef.util.ConstValue.USER_ID
import java.time.LocalDate
import kotlinx.coroutines.launch
import timber.log.Timber

class OrderManageViewModel(private val repository: ChefRepository) : ViewModel() {

    private val upComingList = mutableListOf<Order>()
    private val pendingList = mutableListOf<Order>()
    private val completedList = mutableListOf<Order>()
    private val cancelledList = mutableListOf<Order>()

    private var _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private var _hasData = MutableLiveData<Boolean>()
    val hasData: LiveData<Boolean>
        get() = _hasData


    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var field = ""
    var value = ""

    var liveOrderList = MutableLiveData<List<Order>>()

    init {

        when (UserManger.readData("mode")) {
            Mode.USER.index -> {
                val userId = UserManger.user?.userId?:""
                field = USER_ID
                value = userId
            }
            Mode.CHEF.index -> {
                val chefId = UserManger.user?.chefId?:""
                field = CHEF_ID
                value = chefId
            }
        }
        liveOrderList = repository.getLiveOrder(field, value)
    }

    fun sortOrder(orderList: List<Order>) {
        Timber.d("sortOrderList")

        pendingList.clear()
        upComingList.clear()
        completedList.clear()
        cancelledList.clear()
        if (checkCompleteOrder(orderList)) {

            for (order in orderList) {
                when (order.status) {
                    OrderStatus.PENDING.index -> {
                        pendingList.add(order)
                    }
                    OrderStatus.UPCOMING.index -> {
                        upComingList.add(order)
                    }
                    OrderStatus.COMPLETED.index, OrderStatus.SCORED.index, OrderStatus.APPLIED.index -> {
                        completedList.add(order)
                    }
                    OrderStatus.CANCELLED.index -> {
                        cancelledList.add(order)
                    }
                }
                if (orderList.indexOf(order) == orderList.lastIndex) {
                    _hasData.value = true
                }
            }
        }
    }

    private fun checkCompleteOrder(orderList: List<Order>): Boolean {
        var result = true

        for (order in orderList) {
            when {
                order.status == OrderStatus.UPCOMING.index && order.date < LocalDate.now()
                    .toEpochDay() -> {
                    result = false
                    changeOrderStatus(OrderStatus.COMPLETED.index, order)
                }
                order.status == OrderStatus.PENDING.index && order.date < LocalDate.now()
                    .toEpochDay() -> {
                    result = false
                    changeOrderStatus(OrderStatus.CANCELLED.index, order)
                }
                else -> {
                    result = true
                }
            }
        }
        return result
    }

    private fun changeOrderStatus(status: Int, order: Order) {
        viewModelScope.launch {
            repository.updateOrderStatus(status, order.id)
        }

    }

    fun getList(status: Int) {
        when (status) {
            OrderStatus.PENDING.index -> {
                _orderList.value = pendingList
            }
            OrderStatus.UPCOMING.index -> {
                _orderList.value = upComingList
            }
            OrderStatus.COMPLETED.index -> {
                _orderList.value = completedList
            }
            OrderStatus.CANCELLED.index -> {
                _orderList.value = cancelledList
            }
        }
    }
}
