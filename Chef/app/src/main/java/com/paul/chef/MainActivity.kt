package com.paul.chef

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.paul.chef.data.ProfileInfo
import com.paul.chef.databinding.ActivityMainBinding
import com.paul.chef.ext.getVmFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> { getVmFactory() }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var newUserProfile: ProfileInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


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


        mainViewModel.newUser.observe(this) {
            if (it) {
                navController.navigate(
                    MobileNavigationDirections.actionGlobalChefEditFragment(
                        EditPageType.CREATE_USER.index,
                        newUserProfile
                    )
                )
            } else {
                when (val mode = UserManger.readData("mode")) {
                    Mode.CHEF.index -> {
                        if (UserManger.user?.chefId != null) {
                            turnMode(mode)
                            navController.navigate(
                                MobileNavigationDirections.actionGlobalOrderManageFragment()
                            )
                        } else {
                            turnMode(Mode.USER.index)
                            navController.navigate(
                                MobileNavigationDirections.actionGlobalMenuFragment()
                            )
                        }
                    }
                    else -> {
                        turnMode(Mode.USER.index)
                        navController.navigate(
                            MobileNavigationDirections.actionGlobalMenuFragment()
                        )
                    }
                }
            }
        }

        findNavController(R.id.nav_host_fragment_activity_main)
            .addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
                when (navController.currentDestination?.id) {
                    R.id.menuFragment,
                    R.id.likeFragment,
                    R.id.orderManageFragment,
                    R.id.chatFragment,
                    R.id.userProfileFragment,
                    R.id.calendar_set_open_radio,
                    R.id.transactionFragment,
                    R.id.chefFragment,
                    -> {
                        navView.visibility = View.VISIBLE
                        binding.toolbar6.visibility = View.GONE
                    }
                    R.id.menuDetailFragment,
                    R.id.navigation_home,
                    R.id.loginFragment,
                    -> {
                        navView.visibility = View.GONE
                        binding.toolbar6.visibility = View.GONE
                    }
                    else -> {
                        navView.visibility = View.GONE
                        binding.toolbar6.visibility = View.VISIBLE
                        when (navController.currentDestination?.id) {
                            R.id.bookFragment -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_book_fragment)
                            }
                            R.id.menuEditFragment -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_menu_edit_fragment)
                            }
                            R.id.calendarSetting -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_calendar_set)
                            }
                            R.id.bookSetting -> {
                                binding.toolBarTitle.text = getString(R.string.tool_bar_book_set)
                            }
                            R.id.orderDetailFragment -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_order_detail_fragment)
                            }
                            R.id.displayChefFragment -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_display_chef_fragment)
                            }
                            R.id.termsFragment -> {
                                binding.toolBarTitle.text =
                                    getString(R.string.tool_bar_terms_fragment)
                            }
                            R.id.chatRoomFragment -> {
                                binding.toolBarTitle.text = getString(R.string.tool_bar_chat_room)
                            }
                            else -> {
                                binding.toolBarTitle.text = ""
                            }
                        }
                    }
                }
            }
        binding.mainActivityBackBtn.setOnClickListener {
            navController.navigateUp()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.client_id)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val email = user.email
            val name = user.displayName
            val avatar = user.photoUrl.toString()

            if (name != null && email != null) {
                newUserProfile = ProfileInfo(name, email, avatar)
                mainViewModel.getUser(email)
            }
        } else {
            findNavController(R.id.nav_host_fragment_activity_main).navigate(
                MobileNavigationDirections.actionGlobalLoginFragment()
            )
        }
    }

    fun turnMode(mode: Int) {
        UserManger.saveData(mode)
        if (mode == Mode.USER.index) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
        } else {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.chef_bottom_nav_menu)
        }
    }

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signOut() {
        googleSignInClient.signOut()
        Firebase.auth.signOut()
        UserManger.saveData(Mode.LOGOUT.index)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)

                Toast.makeText(this, "login_success", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {

                Toast.makeText(this, "login_fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w(task.exception, "signInWithCredential:failure")
                    updateUI(null)
                }
            }
    }

    fun block(userId: String, blockMenuList: List<String>?, blockReviewList: List<String>?) {
        if (blockMenuList != null) {
            mainViewModel.block(userId, blockMenuList, null)
        }

        if (blockReviewList != null) {
            mainViewModel.block(userId, null, blockReviewList)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
