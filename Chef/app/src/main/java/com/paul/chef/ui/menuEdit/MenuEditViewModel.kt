package com.paul.chef.ui.menuEdit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.BookSettingType
import com.paul.chef.BookType
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class MenuEditViewModel (private val repository: ChefRepository) : ViewModel() {


    val chefId = UserManger.chef?.id!!

    private var _openBoolean = MutableLiveData<Boolean>()
    val openBoolean: LiveData<Boolean>
        get() = _openBoolean

    init {


        viewModelScope.launch {
            when(val result = repository.getChef(chefId)){
                is Result.Success->{
                    _openBoolean.value = result.data.bookSetting!=null &&result.data.bookSetting.type!=BookSettingType.RefuseAll.index
                }
            }
        }

    }


    fun createMenu(menuName:String,
                   menuIntro:String,
                   perPrice:Int,
                   images:List<String>,
                   discountList:List<Discount>,
                   dishList:List<Dish>,
                   tagList:List<String>,
                   openBoolean: Boolean){
        viewModelScope.launch {
            repository.setMenu(menuName, menuIntro, perPrice, images, discountList, dishList, tagList, openBoolean)
        }
    }

}