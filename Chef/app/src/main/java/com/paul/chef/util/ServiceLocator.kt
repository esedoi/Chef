package com.paul.chef.util

import android.content.Context
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.DefaultChefRepository
import com.paul.chef.data.source.firebase.ChefFirebaseDataSource

object ServiceLocator {

    var repository: ChefRepository? = null

    fun provideRepository(context: Context): ChefRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createChefRepository(context)
        }
    }

    private fun createChefRepository(context: Context): ChefRepository {
        return DefaultChefRepository(
            ChefFirebaseDataSource
        )
    }
}
