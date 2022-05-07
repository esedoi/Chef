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
import com.paul.chef.data.ProfileInfo
import com.paul.chef.data.User


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    var user: User? = null
    var chef: Chef? = null
    var userEmail:String? = null


    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private var _newUser = MutableLiveData<Boolean>()
    val newUser: LiveData<Boolean>
        get() = _newUser

    private var _isChef = MutableLiveData<Boolean>()
    val isChef: LiveData<Boolean>
        get() = _isChef

    private val mutableSelectedItem = MutableLiveData<Int>()
    val selectedItem: LiveData<Int> get() = mutableSelectedItem


    init {
        if(UserManger.user?.userId !=null){

            db.collection("User")
                .document(UserManger.user?.userId?:"")
                .addSnapshotListener { document, e ->
                    if (e != null) {
                        Log.w("notification", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    if(document!=null){
                        val json = Gson().toJson(document.data)
                        val data = Gson().fromJson(json, User::class.java)
                        UserManger.user = data
                        Log.d("mainviewmodel", "update user")
                        if (data.chefId!=null){
                            getChef(data.chefId)
                        }
                    }
                }
            }
         }

    fun getUser(email:String){
        Log.d("mainviewmodel", "fun get user")
        db.collection("User")
            .whereEqualTo("profileInfo.email", email)
            .get()
            .addOnSuccessListener { value ->
                if (value != null) {
                    for (i in value.documents){
                        val item = i.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, User::class.java)
                        UserManger.user = data
                        if (data.chefId!=null){
                            getChef(data.chefId)
                        }
                    }
                    _newUser.value = value.documents.isEmpty()
                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }

//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("notification", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//                if (value != null) {
//                    for(i in value.documents){
//                        val json = Gson().toJson(i.data)
//                        val data = Gson().fromJson(json, User::class.java)
//                            UserManger.user = data
//                        if (data.chefId!=null){
//                            getChef(data.chefId)
//                        }
//                    }
//                    _newUser.value = value.documents.isEmpty()
//                }
//            }
         }




    private fun getChef(chefId:String){
            Log.d("mainviewmodel", "fun getchef")
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
                    UserManger.chef = data
                    }
                 }
             }