package com.paul.chef

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paul.chef.databinding.ActivityMainBinding
import com.paul.chef.ui.datePicker.DatePickerViewModel

class MainActivity : AppCompatActivity() {

    var mode = Mode.CHEF.index

    private lateinit var binding: ActivityMainBinding

//    val navView: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView




//        app:menu="@menu/bottom_nav_menu"

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainViewModel.mode.observe(this) {
            Log.d("mainactivity", "main______________________")
            if (it == Mode.USER.index) {
                navView.menu.clear()
                navView.inflateMenu(R.menu.bottom_nav_menu)
            } else {
                navView.menu.clear()
                navView.inflateMenu(R.menu.chef_bottom_nav_menu)
            }
        }
    }

    fun  turnMode(mode:Int){
        if (mode == Mode.USER.index) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
        } else {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.chef_bottom_nav_menu)
        }
    }
}
