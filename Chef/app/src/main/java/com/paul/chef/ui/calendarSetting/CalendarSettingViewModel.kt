package com.paul.chef.ui.calendarSetting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.DateSetting
import com.paul.chef.data.DateStatus
import com.paul.chef.data.MenuStatus
import com.paul.chef.data.WeekStatus

class CalendarSettingViewModel(application: Application) : AndroidViewModel(application){

    val weekday = mutableListOf<WeekStatus>()
    val date = mutableListOf<DateStatus>()

    private val db = FirebaseFirestore.getInstance()



    fun settingDate(type:Int, close:Int, menuList:List<MenuStatus>,selectDates:List<Long>, selectWeekDay:String, session:List<String>?, startTime:String?, endTime:String?){

        if(type == 1){
            for (i in selectDates){
                val dateStatus = DateStatus(1,i,session, startTime, endTime)
                date.add(dateStatus)
            }

        }else{

    val weekStatus = WeekStatus(1, "三", listOf("11:00"), "10:00", "21:30")
            weekday.add(weekStatus)
        }

        val chefId = "t4E1D2RT0XRuiLBmrTMU"

        val dateSetting = DateSetting(weekday, date)
        val u = mapOf(
                    "menuList" to menuList,
                    "dateSetting" to dateSetting
                )

        db.collection("Chef").document(chefId)
            .update(mapOf(
                "orderSetting.menuList" to menuList,
                    "orderSetting.dateSetting" to dateSetting
            ))


            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }


}