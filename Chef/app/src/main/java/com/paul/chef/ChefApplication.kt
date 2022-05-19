package com.paul.chef

import android.app.Application
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.util.ServiceLocator
import kotlin.properties.Delegates

class ChefApplication : Application() {

    val repository: ChefRepository
        get() = ServiceLocator.provideRepository(this)


    companion object {
        var instance: ChefApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}