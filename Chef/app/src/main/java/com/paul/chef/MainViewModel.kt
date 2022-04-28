package com.paul.chef

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.Chef
import com.paul.chef.data.Order
import com.paul.chef.data.User

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    var user: User? = null
    var chef: Chef? = null


    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private val mutableSelectedItem = MutableLiveData<Int>()
    val selectedItem: LiveData<Int> get() = mutableSelectedItem

    init {
        chef?.let {
            db.collection("Chef")
                .document(it.id)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.w("notification", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    val item = value?.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    chef = data
                    Log.d("mainviewModel", "chef=$chef")

                }
        }

        user?.let {
            db.collection("User")
                .document(it.userId)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.w("notification", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    val item = value?.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, User::class.java)
                    user = data
                    Log.d("mainviewModel", "chef=$user")

                }
        }
    }




    fun getChef(chefId:String){
            Log.d("mainviewmodel", "+++++++++++++++++++++chefid=$chefId")
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
                    chef = data
                    Log.d("mainviewModel", "chef=$chef")

                    }
                 }
             }