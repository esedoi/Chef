package com.paul.chef

import android.content.Context
import android.content.SharedPreferences
import com.paul.chef.data.Chef
import com.paul.chef.data.User

class UserManger {

    val userFee = 300

    companion object {

        var user: User? = null
        var chef: Chef? = null
        var lastSelectedPosition = -1
        var tempMode = -1

        private lateinit var settings: SharedPreferences

        fun readData(mode: String): Int {
            settings = ChefApplication.instance.getSharedPreferences(
                "DATA",
                Context.MODE_PRIVATE
            )

            return settings.getInt(mode, 1)
        }

        fun saveData(mode: Int) {
            tempMode = mode
            settings = ChefApplication.instance.getSharedPreferences(
                "DATA",
                Context.MODE_PRIVATE
            )
            settings.edit()
                .putInt("mode", mode)
                .apply()
        }
    }
}
