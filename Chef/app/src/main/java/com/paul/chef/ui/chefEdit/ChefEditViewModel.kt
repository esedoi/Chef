package com.paul.chef.ui.chefEdit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.*

class ChefEditViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext



    private val db = FirebaseFirestore.getInstance()





    fun createChef(name:String, email:String, intro:String){
        val id = db.collection("Chef").document().id
        val profileInfo = ProfileInfo(name, email, "imagesString",intro)

        val temp = Chef(id, profileInfo)


        //set firebase資料
        db.collection("Chef").document(id)
            .set(temp)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }

    fun saveChef(chefId:String, name:String, email:String, intro:String){

        val info = ProfileInfo(name, email, "jackimage",intro)

        db.collection("Chef").document(chefId)
            .update(mapOf(
                "profileInfo" to info,
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }
}


