package com.paul.chef.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.data.Transaction
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import java.util.*
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: ChefRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val unpaidList = mutableListOf<Order>()
    val processingList = mutableListOf<Transaction>()
    val receivedList = mutableListOf<Transaction>()

    private var _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private var _transactionList = MutableLiveData<List<Transaction>>()
    val transactionList: LiveData<List<Transaction>>
        get() = _transactionList

    val chefId = UserManger.chef?.id!!

    var liveOrderList = MutableLiveData<List<Order>>()

    init {
        liveOrderList = repository.getLiveOrder("chefId", UserManger.user?.chefId!!)
    }

    fun getList(position: Int) {
        processingList.clear()
        receivedList.clear()
        viewModelScope.launch {
            when (val result = repository.getTransaction()) {
                is Result.Success -> {
                    for (transaction in result.data) {
                        sortTransactionList(transaction)

                        if (result.data.indexOf(transaction) == result.data.lastIndex) {
                            toLiveData(position)
                        }
                    }
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }
        }
    }

    private fun toLiveData(position: Int) {
        when (position) {
            1 -> {
                _transactionList.value = processingList
            }
            2 -> {
                _transactionList.value = receivedList
            }
        }
    }

    private fun sortTransactionList(transaction: Transaction) {
        when (transaction.status) {
            TransactionStatus.PROCESSING.index -> {
                processingList.add(transaction)
            }
            TransactionStatus.COMPLETED.index -> {
                receivedList.add(transaction)
            }
        }
    }

    fun getUnpaidList(orderList: List<Order>) {
        unpaidList.clear()
        for (order in orderList) {
            when (order.status) {
                OrderStatus.COMPLETED.index, OrderStatus.SCORED.index -> {
                    unpaidList.add(order)
                }
            }

            if (orderList.indexOf(order) == orderList.lastIndex) {
                _orderList.value = unpaidList
            }
        }
    }

    fun changeStatus(orderId: String, status: Int) {
        viewModelScope.launch {
            repository.updateOrderStatus(status, orderId)
        }
    }

    fun applyMoney(chefReceive: Int) {
        val id = db.collection("Transaction").document().id
        val time = Calendar.getInstance().timeInMillis
        val idList = mutableListOf<String>()

        for (i in unpaidList) {
            idList.add(i.id)
        }

        if (idList.isNotEmpty() && chefReceive != 0) {
            val transaction = Transaction(
                id,
                chefId,
                time,
                chefReceive,
                idList,
                TransactionStatus.PROCESSING.index
            )

            viewModelScope.launch {
                repository.setTransaction(transaction)
            }
        }
    }
}
