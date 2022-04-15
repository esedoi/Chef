package com.paul.chef.ui.orderSetting

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.*

class OrderSettingViewModel(application: Application) : AndroidViewModel(application) {

    val weekday = mutableListOf<WeekStatus>()
    val date = mutableListOf<DateStatus>()

    private val db = FirebaseFirestore.getInstance()


    fun setting() {

        val chefId = "t4E1D2RT0XRuiLBmrTMU"


        //orderSetting
        val type1Setting = ChefSpace(6, listOf("12:00", "18:00"))
        val type2Setting = UserSpace(10, "10:00", "21:00")


        db.collection("Chef").document(chefId)
            .update(
                mapOf(

                    "orderSetting.type" to 3,
                    "orderSetting.defaultDateStatus" to 0,
                    "orderSetting.type1Setting" to type1Setting,
                    "orderSetting.type2Setting" to type2Setting

                )
            )
            .addOnSuccessListener {
                Log.d(
                    "notification",
                    "DocumentSnapshot successfully updated!"
                )
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }


}