package com.paul.chef


import android.content.Context
import android.content.SharedPreferences
import com.paul.chef.data.Chef
import com.paul.chef.data.User

class UserManger  {


    val userFee = 300
//    val userId = "V6FaQPxE9EskaO2v27fl"
//    val userAvatar = "userPic"



    companion object  {
        //            private lateinit var settings: SharedPreferences
//            var loginStatus: Boolean = false
//            var userToken: String = "userToken"
//        @SuppressLint("StaticFieldLeak")


        var user: User?=null
        var chef: Chef?=null
        var lastSelectedPosition = -1
        var tempMode = -1


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
            tempMode = mode
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