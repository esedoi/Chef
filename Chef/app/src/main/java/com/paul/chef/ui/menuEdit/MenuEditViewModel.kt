package com.paul.chef.ui.menuEdit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.*

class MenuEditViewModel (application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()
    val chefId = "9qKTEyvYbiXXEJSjDJGF"



    fun createMenu(menuName:String,
                   menuIntro:String,
                   perPrice:Int,
                   images:List<String>,
                   discountList:List<Discount>,
                   dishList:List<Dish>){
        val id = db.collection("Menu").document().id


        val menu = ChefMenu(id, chefId, menuName, menuIntro, perPrice, images, discountList, dishList)


        //set firebase資料
        db.collection("Menu").document(id)
            .set(menu)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }


}