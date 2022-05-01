package com.paul.chef.ui.userProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.Order
import com.paul.chef.data.ProfileInfo
import com.paul.chef.data.User

class UserProfileViewModel : ViewModel() {

    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private var _getChefDone = MutableLiveData<Boolean>()
    val getChefDone: LiveData<Boolean>
        get() = _getChefDone

    private val db = FirebaseFirestore.getInstance()

       fun createUser(name:String, email:String, intro:String){
           val id = db.collection("User").document().id
           val profileInfo = ProfileInfo(name, email, "imagesString",intro)
           val temp = User(id, profileInfo)

           //set firebase資料
           db.collection("User").document(id)
               .set(temp)
               .addOnSuccessListener { documentReference ->
                   Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
               }
               .addOnFailureListener { e ->
                   Log.w("click", "Error adding document", e)
               }
       }

    fun createChef(user:User){

        val id = db.collection("Chef").document().id

        val profileInfo = user.profileInfo
        val userId = UserManger.user.userId
        if(profileInfo!=null&&userId!=null){
            val temp = Chef(id, profileInfo)
            //set firebase資料
            db.collection("Chef").document(id)
                .set(temp)
                .addOnSuccessListener { documentReference ->
                    Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")

                }
                .addOnFailureListener { e ->
                    Log.w("click", "Error adding document", e)
                }

            db.collection("User").document(userId)
                .update(mapOf(
                    "chefId" to id
                ))
                .addOnSuccessListener { documentReference ->
                    Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w("click", "Error adding document", e)
                }
        }
        _chefId.value = id

    }


    fun getChef(chefId:String){
        Log.d("userprofileviewmodel", "+++++++++++++++++++++chefid=$chefId")
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
                _getChefDone.value = true
            }
        }
    }