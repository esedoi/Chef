package com.paul.chef.ext

import android.app.Activity
import android.widget.Toast
import com.paul.chef.ChefApplication
import com.paul.chef.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as ChefApplication).repository
    return ViewModelFactory(repository)
}

fun Activity.showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).
        show()
    }



