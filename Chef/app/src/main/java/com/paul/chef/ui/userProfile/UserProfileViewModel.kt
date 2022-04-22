package com.paul.chef.ui.userProfile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.Chef
import com.paul.chef.data.ProfileInfo
import com.paul.chef.data.User

class UserProfileViewModel : ViewModel() {

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

}