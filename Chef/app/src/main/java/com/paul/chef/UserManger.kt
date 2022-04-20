package com.paul.chef

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.CalendarContract

class UserManger  {

    var mode = Mode.USER.index


    companion object  {
        //            private lateinit var settings: SharedPreferences
//            var loginStatus: Boolean = false
//            var userToken: String = "userToken"
        @SuppressLint("StaticFieldLeak")
//            private var context: Context = Context


        //宣告繼承 SharedPreferences 的變數
        private lateinit var settings: SharedPreferences


        fun readData(userToken: String, context: Context): String? {

//            settings = context.getSharedPreferences(
//                "DATA",
//                Context.MODE_PRIVATE
//            )



            return settings.getString(userToken, "")

        }

        fun saveData(token:String, context: Context) {
            //使用 getSharedPreferences() 方法
            //參數前面放入資料夾名稱，後面放入MODE
            // MODE_PRIVATE 表示只有建立該檔案的應用才能訪問他
            settings = context.getSharedPreferences(
                "DATA",
                Context.MODE_PRIVATE
            )
            //使用edit()添加數據
            settings.edit()
                .putString("userToken",token )
                .putInt("age",18 )
                .putBoolean("status",false )
                //調用apply()提交數據
                .apply()
        }



    }

}