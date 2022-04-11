package com.paul.chef.ui.book

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.*
import java.time.LocalDate
import java.util.*

class BookViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val db = FirebaseFirestore.getInstance()


//    val userId:String,
//    val userEmail:String,
//    val chefId:String, //chefId or kitchenId
////    val chefEmail:String,
//    val date:LocalDate,
////    val time:String,
//    val note:String,
//    val people:Int,
//    val orderTime:Long,
//    val menuId:String,
//    val status:Int, //即將到來,已取消,已完成
//    val originalPrice:Int,
//    val discount:Int,
//    val total:Int
//)


    fun book(chefMenu: ChefMenu, datePicker:Long) {

        val orderId = db.collection("Order").document().id
        val userName = "harris"
        val userId = "77777777"
        val userEmail = "harris@gmail.com"
        val chefId = "9qKTEyvYbiXXEJSjDJGF"
        val date = datePicker
        val note= "不要加辣"
        val people = 9
        val time = Calendar.getInstance().timeInMillis
        val menuId = "WY8R85RyloUzKaTpsKsO"
        val status = 0
        val originalPrice = chefMenu.perPrice * people
        var total = originalPrice



        for(i in chefMenu.discount){
            if(people>=i.people){
                val d:Double = (i.percentOff.toDouble() / 100)
                Log.d("bookviewmodel", "d=$d")
                total = (originalPrice * (1 - d)).toInt()

                Log.d("bookviewmodel", "total = $total")
            }
        }

        val discount = originalPrice - total

        Log.d("bookviewmodel", "original = $originalPrice")
        Log.d("bookviewmodel", "total = $total")
        Log.d("bookviewmodel", "discount = $discount")

        val order = OrderTable(
            orderId,
            userName,
            userId,
            userEmail,
            chefId,
            date,
            note,
            people,
            time,
            menuId,
            status,
            originalPrice,
            discount,
            total
          )

        //set firebase資料
        db.collection("Order").document(orderId)
            .set(order)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }
}


