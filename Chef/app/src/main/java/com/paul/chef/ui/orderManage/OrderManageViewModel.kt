package com.paul.chef.ui.orderManage


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.data.source.ChefRepository

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

    var field = ""
    var value = ""

    var liveOrderList = MutableLiveData<List<Order>>()


    init {

        when (UserManger.readData("mode")) {
            Mode.USER.index -> {
                val userId = UserManger.user?.userId!!
                field = "userId"
                value = userId
            }
            Mode.CHEF.index -> {
                val chefId = UserManger.user?.chefId!!
                field = "chefId"
                value = chefId
            }
        }
        liveOrderList = repository.getLiveOrder(field, value)
    }

    fun sortOrder(orderList: List<Order>) {
        pendingList.clear()
        upComingList.clear()
        completedList.clear()
        cancelledList.clear()

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