package com.paul.chef.ui.bottomSheetPicker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Chef

class PickerViewModel(application: Application) : AndroidViewModel(application) {

    private var _bookSetting = MutableLiveData<BookSetting>()
    val bookSetting: LiveData<BookSetting>
        get() = _bookSetting


    private val db = FirebaseFirestore.getInstance()
    fun getBookSetting(chefId: String) {
        db.collection("Chef")
            .document(chefId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    _bookSetting.value = data.bookSetting!!
                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }

    }
}