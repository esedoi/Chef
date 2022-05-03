package com.paul.chef.ui.calendar

import android.app.Application
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.ChefManger
import com.paul.chef.UserManger
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Chef
import com.paul.chef.data.DateStatus
import com.paul.chef.data.Order
import java.time.LocalDate

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    private val dateList = mutableListOf<LocalDate>()
    private var _orderList = MutableLiveData<List<LocalDate>>()
    val orderList: LiveData<List<LocalDate>>
        get() = _orderList


    private val dateStatus = mutableListOf<DateStatus>()
    private var _dateSetting = MutableLiveData<List<DateStatus>>()
    val dateSetting: LiveData<List<DateStatus>>
        get() = _dateSetting

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    val chefId = UserManger.chef?.id

    init {
        db.collection("Order")
            .whereEqualTo("chefId", chefId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.d("calendarviewmodel", "接收到order資料")
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Order::class.java)
                    val localDate: LocalDate = LocalDate.ofEpochDay(data.date)
//                    val sdf = SimpleDateFormat("YYYY-MM-dd")
//                    val day = sdf.format(data.date)
//                    val localDate: LocalDate = LocalDate.parse(day)
                    Log.d("calendarviewmodel", "order_data.date=${data.date}")
                    Log.d("calendarviewmodel", "order_localdate=${localDate}")
                    dateList.add(localDate)
                }
                _orderList.value = dateList
            }

        db.collection("Chef")
            .document(chefId).collection("dateSetting")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null) {
                    for(i in value.documents){
                        val item = i.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, DateStatus::class.java)
                        dateStatus.add(data)
                    }
                    _dateSetting.value = dateStatus
                }

            }

        db.collection("Chef")
            .document(chefId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                    val item = value?.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                if(data.bookSetting!=null){
                    _bookSetting.value = data.bookSetting!!
                }
                Log.d("calendarviewmodel", "接收到Chef資料=$data")
            }

    }
}