package com.paul.chef.ui.userProfile

import android.os.UserManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.UserManger
import com.paul.chef.data.Chef
import com.paul.chef.data.Order
import com.paul.chef.data.ProfileInfo
import com.paul.chef.data.User

class UserProfileViewModel : ViewModel() {

    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

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

    fun createChef(name:String, email:String, intro:String, avatar:String){

        val id = db.collection("Chef").document().id

        val profileInfo = ProfileInfo(name, email, avatar,intro)
        val userId = UserManger().userId
        val temp = Chef(id, profileInfo)

        //set firebase資料
        db.collection("Chef").document(id)
            .set(temp)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                _chefId.value = id
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

}