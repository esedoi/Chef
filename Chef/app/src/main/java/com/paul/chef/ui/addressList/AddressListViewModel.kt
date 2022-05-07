package com.paul.chef.ui.addressList

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
import com.paul.chef.data.Address
import com.paul.chef.data.User

@SuppressLint("StaticFieldLeak")
class AddressListViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private var _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>>
        get() = _addressList

   var _lastSelection = MutableLiveData<Int>()
    val lastSelection: LiveData<Int>
        get() = _lastSelection

    private val db = FirebaseFirestore.getInstance()
    val userId = UserManger.user?.userId



    init {

        if (userId != null) {
            db.collection("User")
                .document(userId)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w("notification", "Listen failed.", error)
                        return@addSnapshotListener
                    }
                    val item = value?.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, User::class.java)

                     data.address?.let { _addressList.value = it}
                }
            }
        }

    fun updateAddress(addressList: List<Address>) {

        if (userId != null) {
            db.collection("User")
                .document(userId)
                .update(
                    mapOf(
                        "address" to addressList,
                    )
                )
                .addOnSuccessListener {
                    Log.d(
                        "notification",
                        "DocumentSnapshot successfully updated!"
                    )
                }
                .addOnFailureListener { exception ->
                    Log.d("pickerViewModel", "get failed with ", exception)
                }
            }
        }
    }