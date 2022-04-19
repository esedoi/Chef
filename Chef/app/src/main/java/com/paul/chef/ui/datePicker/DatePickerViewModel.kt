package com.paul.chef.ui.datePicker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.ChefManger
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Chef
import com.paul.chef.data.DateStatus
import com.paul.chef.data.Order
import java.time.LocalDate

class DatePickerViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
//    lateinit var chefId:String

    private val dateStatus = mutableListOf<DateStatus>()
    private var _dateSetting = MutableLiveData<List<DateStatus>>()
    val dateSetting: LiveData<List<DateStatus>>
        get() = _dateSetting

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting


    init {
        val chefId = ChefManger().chefId


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
                _bookSetting.value = data.bookSetting!!
                Log.d("datepickerviewmodel", "接收到Chef資料=$data")
            }
    }
}