package com.paul.chef.ui.dishAdd

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel

class AddDishViewModel(application: Application) : AndroidViewModel(application){


}

//
//data class Dish(
//    val type:String, //甜點、開胃菜
//    val option:Int, //固定菜色, 可替換, 加價替換
//    val extraPrice:Int,
//    val name:String
//): Parcelable