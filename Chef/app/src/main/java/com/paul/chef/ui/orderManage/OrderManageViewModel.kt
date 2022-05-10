package com.paul.chef.ui.orderManage

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.*
import com.paul.chef.data.Order

class OrderManageViewModel(application: Application) : AndroidViewModel(application){
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext


    private val db = FirebaseFirestore.getInstance()


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



    init{
        val mode =UserManger.readData("mode", context)

        when(mode){
            Mode.USER.index->{
                val userId = UserManger.user?.userId!!
                field = "userId"
                value = userId
            }
            Mode.CHEF.index->{
                val chefId = UserManger.chef?.id!!
                field = "chefId"
                value = chefId
            }
        }

        Log.d("field", "field=$field")

        db.collection("Order")
            .whereEqualTo( field , value)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                pendingList.clear()
                upComingList.clear()
                completedList.clear()
                cancelledList.clear()
                for (doc in value!!.documents) {
                    Log.d("ordermangeviewmodel", "doc=$doc")
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Order::class.java)
                    Log.d("ordermangeviewmodel", "接收到order資料${data}")

                    when(data.status){
                        OrderStatus.PENDING.index->{
                            pendingList.add(data)
                        }
                        OrderStatus.UPCOMING.index->{
                            upComingList.add(data)
                        }
                        OrderStatus.COMPLETED.index, OrderStatus.SCORED.index, OrderStatus.APPLIED.index->{
                            completedList.add(data)
                        }
                        OrderStatus.CANCELLED.index->{
                            cancelledList.add(data)
                        }
                    }
                }
                _hasData.value = true
            }
    }

    fun getList(status:Int){
        Log.d("orderviewmodel", "status=$status")
        Log.d("orderviewmodel", "OrderStatus.PENDING.index=${OrderStatus.PENDING.index}")

        when(status) {
            OrderStatus.PENDING.index -> {
                if(pendingList.size>=1) {
                    Log.d("orderviewmodel", "pendingList=$pendingList")
                    _orderList.value = pendingList
                }
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