package com.paul.chef.util

import com.paul.chef.ChefApplication
import com.paul.chef.R

object Util {

//    fun getString(resourceId: Int): String {
//        return ChefApplication.instance.getString(resourceId)
//    }


    fun getPrice(price:Int):String{
        val str = String.format("%,d", price)
        return ChefApplication.instance.getString(R.string.new_taiwan_dollar, str)
    }
}