package com.paul.chef.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.User

class HomeViewModel : ViewModel() {

    private var _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData

    private val db = FirebaseFirestore.getInstance()
    val userName=""
    val userIntro = ""
    val userAvatar= ""

    fun login(){
        val userId = UserManger.user?.userId!!
        db.collection("User")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, User::class.java)
                    _userData.value = data

                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }

   }
}
