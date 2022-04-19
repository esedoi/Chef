package com.paul.chef

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paul.chef.data.Chef

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _mode = MutableLiveData<Int>()
    val mode: LiveData<Int>
        get() = _mode

    private val mutableSelectedItem = MutableLiveData<Int>()
    val selectedItem: LiveData<Int> get() = mutableSelectedItem

    fun turnMode(it:Int){
        Log.d("mainviewmodel", "here__________________${it}")

        _mode.value = it
    }
}