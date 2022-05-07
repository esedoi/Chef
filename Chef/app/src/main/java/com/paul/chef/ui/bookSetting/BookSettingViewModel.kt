package com.paul.chef.ui.bookSetting

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.BookSetting

class BookSettingViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()
    val chefId = UserManger.user?.chefId!!

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting

    init {
        db.collection("Chef")
            .document(chefId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    if(data.bookSetting!=null){
                        Log.d("booksettingviewmodel", "data.booksetting=${data.bookSetting}")
                        _bookSetting.value = data.bookSetting!!
                    }


                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }
    }


    fun setting(type:Int, calendarDefault:Int, chefSpace:ChefSpace, userSpace:UserSpace) {

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