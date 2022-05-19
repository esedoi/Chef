package com.paul.chef.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.MainViewModel
import com.paul.chef.data.User
import com.paul.chef.data.source.ChefRepository

class UserViewModelFactory constructor(
    private val repository: ChefRepository,
    private val user: User?
): ViewModelProvider.NewInstanceFactory()  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T  =
        with(modelClass){
            when{
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel( repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }

        }  as T
}