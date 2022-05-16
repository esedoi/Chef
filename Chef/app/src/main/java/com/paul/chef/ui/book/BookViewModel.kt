package com.paul.chef.ui.book

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.ChefManger
import com.paul.chef.UserManger
import com.paul.chef.data.*
import java.util.*

class BookViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val db = FirebaseFirestore.getInstance()

    private var _chefSpaceAddress = MutableLiveData<Address>()
    val chefSpaceAddress: LiveData<Address>
        get() = _chefSpaceAddress

    private var _bookDone = MutableLiveData<Boolean>()
    val bookDone: LiveData<Boolean>
        get() = _bookDone

    var userPay = -1
    var chefReceive = -1




//    init{
//        val chefId = ChefManger().chefId
//        db.collection("Chef")
//            .document(chefId)
//            .addSnapshotListener { value, e ->
//                if (e != null) {
//                    Log.w("notification", "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//                Log.d("bookviewmodel", "接收到Chef")
//                val item = value?.data
//                val json = Gson().toJson(item)
//                val data = Gson().fromJson(json, Chef::class.java)
//                _bookSetting.value = data.bookSetting!!
//            }
//    }

    private var _priceResult = MutableLiveData<Map<String,Int>>()
    val priceResult: LiveData<Map<String,Int>>
        get() = _priceResult

    fun getAddress(chefId:String){
        db.collection("Chef")
            .document(chefId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {

                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Chef::class.java)
                       _chefSpaceAddress.value =  data.bookSetting?.chefSpace?.address

                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }
    }


    fun orderPrice(menu: Menu, people:Int){
        val originalPrice = menu.perPrice * people
        var total = originalPrice
        var isDiscount = 0
        var userFee = UserManger().userFee
        var chefFee = ChefManger().chefFee

        for(i in menu.discount){
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
         userPay = total+userFee
        chefReceive = total-chefFee
        _priceResult.value = mapOf(
            "discountPerPrice" to discountPerPrice,
            "originalPrice" to originalPrice,
            "total" to total,
            "userPay" to userPay,
            "userFee" to userFee,
            "isDiscount" to isDiscount

        )

    }


    fun book(menu: Menu, type:Int, address:Address, datePicker:Long, time:String, note:String, people:Int, selectedDish:List<Dish> ) {

        val orderId = db.collection("Order").document().id
        val userId = UserManger.user?.userId!!
        val userName = UserManger.user!!.profileInfo?.name!!
        val chefName = menu.chefName
        //這裡要改成絕對不會空
        val userPic = UserManger.user!!.profileInfo?.avatar?:"nullPic"
        val chefPic = menu.chefAvatar
        val menuName = menu.menuName
        val chefId = menu.chefId
        val orderTime = Calendar.getInstance().timeInMillis
        val date = datePicker
        val menuId = menu.id
        val status = 0
        val originalPrice = menu.perPrice * people
        var total = originalPrice

        for(i in menu.discount){
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
            userName,
            chefName,
            userPic,
            chefPic,
            menuName,
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
            userPay,
            chefReceive
          )

        //set firebase資料
        db.collection("Order").document(orderId)
            .set(order)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                _bookDone.value = true
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }
    }
}


