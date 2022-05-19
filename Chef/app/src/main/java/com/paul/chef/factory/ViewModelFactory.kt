package com.paul.chef.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.MainViewModel
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.ui.menu.MenuListViewModel
import com.paul.chef.ui.menuDetail.MenuDetailViewModel
import com.paul.chef.ui.menuEdit.MenuEditViewModel
import com.paul.chef.ui.orderDetail.OrderDetailViewModel
import com.paul.chef.ui.review.ReviewViewModel
import com.paul.chef.ui.userProfile.UserProfileViewModel

class ViewModelFactory constructor(
    private val repository: ChefRepository
):ViewModelProvider.NewInstanceFactory()  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T  =
        with(modelClass){
            when{
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)
                isAssignableFrom(UserProfileViewModel::class.java)->
                    UserProfileViewModel(repository)
                isAssignableFrom(ReviewViewModel::class.java)->
                    ReviewViewModel(repository)
                isAssignableFrom(OrderDetailViewModel::class.java)->
                    OrderDetailViewModel(repository)
                isAssignableFrom(MenuEditViewModel::class.java)->
                    MenuEditViewModel(repository)
                isAssignableFrom(MenuDetailViewModel::class.java)->
                    MenuDetailViewModel(repository)
                isAssignableFrom(MenuListViewModel::class.java)->
                    MenuListViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }

        }  as T
    }