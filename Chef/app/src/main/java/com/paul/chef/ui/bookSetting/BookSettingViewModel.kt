package com.paul.chef.ui.bookSetting

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.UserManger
import com.paul.chef.data.*

class BookSettingViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()


    fun setting(type:Int, calendarDefault:Int, chefSpace:ChefSpace, userSpace:UserSpace) {

        val chefId = UserManger.user?.chefId

        if (chefId != null) {
            db.collection("Chef").document(chefId)
                .update(
                    mapOf(
                        "bookSetting.type" to type,
                        "bookSetting.calendarDefault" to calendarDefault,
                        "bookSetting.userSpace" to userSpace,
                        "bookSetting.chefSpace" to chefSpace
                    )
                )
                .addOnSuccessListener {
                    Log.d(
                        "notification",
                        "DocumentSnapshot successfully updated!"
                    )
                    Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
        }

    }


}