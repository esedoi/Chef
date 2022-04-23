package com.paul.chef

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.CalendarContract

class UserManger  {


    val userFee = 300
    val userId = "V6FaQPxE9EskaO2v27fl"
    val userAvatar = "userPic"



    companion object  {
        //            private lateinit var settings: SharedPreferences
//            var loginStatus: Boolean = false
//            var userToken: String = "userToken"
//        @SuppressLint("StaticFieldLeak")
//            private var context: Context = Context


        //宣告繼承 SharedPreferences 的變數
        private lateinit var settings: SharedPreferences


        fun readData(mode: String, context: Context): Int? {

            settings = context.getSharedPreferences(
                "DATA",
                Context.MODE_PRIVATE
            )

            return settings.getInt(mode, 1)

        }

        fun saveData(mode:Int, context: Context) {
            settings = context.getSharedPreferences(
                "DATA",
                Context.MODE_PRIVATE
            )
            settings.edit()
                .putInt("mode",mode )
                .apply()
        }



    }

}