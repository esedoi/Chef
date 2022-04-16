package com.paul.chef.ui.book

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.*
import java.util.*

class BookViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val db = FirebaseFirestore.getInstance()

//    data class Order(
//        val id:String, //D2204130685964
//        val userId:String,
//        val chefId:String, //chefId or kitchenId
//        val type:Int, //userspace, chef space
//        val address: String,
//        val orderTime:Long,
//        val date:Long,
//        val time:String,
//        val note:String,
//        val people:Int,
//        val menuId:String,
//        val selectedDish: List<Dish>,
//        val status:Int, //即將到來,已取消,已完成
//        val originalPrice:Int,
//        val discount:Int,
//        val total:Int,
//    )

    var _priceResult = MutableLiveData<Map<String,Int>>()
    val priceResult: LiveData<Map<String,Int>>
        get() = _priceResult


    fun orderPrice(chefMenu: ChefMenu, people:Int){
        val originalPrice = chefMenu.perPrice * people
        var total = originalPrice
        var isDiscount = 0
        var userFee = 300

        for(i in chefMenu.discount){
            if(people>=i.people){
                val d:Double = (i.percentOff.toDouble() / 100)
                Log.d("bookviewmodel", "d=$d")
                total = (originalPrice * (1 - d)).toInt()
                Log.d("bookviewmodel", "total = $total")
                isDiscount = 1
            }
        }
        val discountPerPrice = total/people
        val discount = originalPrice-total
        val userPay = total+userFee
        _priceResult.value = mapOf(
            "discountPerPrice" to discountPerPrice,
            "originalPrice" to originalPrice,
            "total" to total,
            "userPay" to userPay,
            "userFee" to userFee,
            "isDiscount" to isDiscount

        )

    }


    fun book(chefMenu: ChefMenu,type:Int,address:String,datePicker:Long,time:String, note:String,people:Int, selectedDish:List<Dish> ) {

        val orderId = db.collection("Order").document().id
        val userId = "77777777"
        val chefId = "9qKTEyvYbiXXEJSjDJGF"
        val orderTime = Calendar.getInstance().timeInMillis
        val date = datePicker
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

        val order = Order(
            orderId,
            userId,
            chefId,
            type,
            address,
            orderTime,
            date,
            time,
            note,
            people,
            menuId,
            selectedDish,
            status,
            originalPrice,
            discount,
            total,
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


