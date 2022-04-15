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





    fun createChef(){
        val id = db.collection("Chef").document().id
        val profileInfo = ProfileInfo("paul", "email@email.com", "imagesString","i am master chef")
        val address = listOf<String>("address____iwefiwefweqwdqwd")

        val temp = Chef(id, profileInfo, bankInfo = null, address, orderSetting = null )


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

    fun saveChef(chefId:String){

        val info = ProfileInfo("jack", "jack@email.com", "jackimage","i am master chef!!")

        db.collection("Chef").document(chefId)
            .update(mapOf(
                "chefInfo" to info,
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }
}


