package com.paul.chef.ext

import androidx.fragment.app.Fragment
import com.paul.chef.ChefApplication
import com.paul.chef.data.User
import com.paul.chef.factory.UserViewModelFactory
import com.paul.chef.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as ChefApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(user: User?): UserViewModelFactory {
    val repository = (requireContext().applicationContext as ChefApplication).repository
    return UserViewModelFactory(repository, user)
}
