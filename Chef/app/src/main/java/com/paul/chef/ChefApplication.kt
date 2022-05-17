package com.paul.chef

import android.app.Application
import kotlin.properties.Delegates

class ChefApplication : Application() {

    // Depends on the flavor,
//    val stylishRepository: StylishRepository
//        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: ChefApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}