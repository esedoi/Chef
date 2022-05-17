package com.paul.chef.ui.calendarSetting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.UserManger
import com.paul.chef.data.DateStatus
import java.time.LocalDate

class CalendarSettingViewModel(application: Application) : AndroidViewModel(application) {

    val date = mutableListOf<DateStatus>()

    private val db = FirebaseFirestore.getInstance()


    fun settingDate(
        status: Int,
        selectDates: List<LocalDate>,
    ) {
            for (i in selectDates) {
                val long = i.toEpochDay()
                val dateStatus = DateStatus(status, long)
                date.add(dateStatus)
            }
        val chefId = UserManger.chef?.id!!


        for (i in date) {
            db.collection("Chef").document(chefId)
                .collection("dateSetting").document(i.date.toString())
                .set(
                    mapOf(
                        "date" to i.date,
                        "status" to i.status
                    )
                )
                .addOnSuccessListener {
                    Log.d("notification", "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
        }
    }
}