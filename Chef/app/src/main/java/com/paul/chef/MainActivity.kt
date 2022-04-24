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
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.paul.chef.databinding.ActivityMainBinding
import com.paul.chef.ui.datePicker.DatePickerViewModel

class MainActivity : AppCompatActivity() {



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




        when(val mode = UserManger.readData("mode", this)){
            Mode.CHEF.index->{
                turnMode(mode)
                navController.navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())

            }
            Mode.USER.index->{
                turnMode(mode)
                navController.navigate(MobileNavigationDirections.actionGlobalMenuFragment())
            }
        }

        findNavController(R.id.nav_host_fragment_activity_main).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when(navController.currentDestination?.id){
                R.id.menuFragment,
                R.id.likeFragment,
                R.id.orderManageFragment,
                R.id.chatFragment,
                R.id.userProfileFragment,
                R.id.calendar,
                R.id.transactionFragment,
                R.id.chefFragment ->{ navView.visibility = View.VISIBLE
                }
                else->{
                    navView.visibility = View.GONE
                }
            }
//
        }

    }

    fun  turnMode(mode:Int){

        UserManger.saveData(mode,this)

        if (mode == Mode.USER.index) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
        } else {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.chef_bottom_nav_menu)
        }
    }

    fun hideNaveView(){
        binding.navView.visibility = View.GONE
    }
    fun showNaveView(){
        binding.navView.visibility = View.VISIBLE
    }
}
