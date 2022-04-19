package com.paul.chef.ui.chef

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.Chef
import com.paul.chef.data.ChefMenu

class ChefViewModel(application: Application) : AndroidViewModel(application) {


    private val db = FirebaseFirestore.getInstance()


    private var _chefInfo = MutableLiveData<Chef>()
    val chefInfo: LiveData<Chef>
        get() = _chefInfo


    init {
        db.collection("Chef")
            .whereEqualTo("id", "wbdfKqWPi0SDOL7bWA6G")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    _chefInfo.value = data
                }
            }
    }
}
