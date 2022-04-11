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



    fun createMenu(){
        val id = db.collection("Menu").document().id


        val images = listOf<String>("1010193913", "sssqqqooeoeoe", "lllzzoaodwddqqq")
        val dishName0 = DishName(0, "甘泉魚麵")
        val dishName1 = DishName(100, "小羔羊")
        val dishName3 = DishName(0, "肉桂捲")
        val dishName4 = DishName(0, "起司濃湯")
        val dishName5 = DishName(0, "法國麵包")
        val dishName6 = DishName(120, "香檳")
        val dishNameList0 = listOf<DishName>(dishName0, dishName1)
        val dishNameList1 = listOf(dishName3)
        val dishNameList2 = listOf(dishName4)

        val dish0 = Dish("主菜", 1, dishNameList0)
        val dish1 = Dish("甜點", 0, dishNameList1)
        val dish2 = Dish("湯", 0, dishNameList2)
        val dish3 = Dish("開胃菜", 0, dishNameList2)
        val dish4 = Dish("酒", 0, dishNameList2)

        val dishes = listOf(dish0, dish1, dish2,dish3, dish4)
        val perPrice = 1900
        val discount = listOf(Discount(5, 20))
        val defaultTime = DefaultTime(true, "18:30", "22:30")
        val orderSetting = OrderSetting(1, defaultTime, override = null, 20, "5")

        val menu = ChefMenu(id, images, dishes, chefId, reviewRating = null , perPrice, discount, orderSetting)


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
    fun saveMenu(){
    }

}