package com.paul.chef

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
//    lateinit var mainViewModel: MainViewModel
    private val mainViewModel by viewModels<MainViewModel>{ getVmFactory() }



    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var newUserProfile: ProfileInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mainactivity", "oncreate")



//        mainViewModel =
//            ViewModelProvider(this).get(MainViewModel::class.java)



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

//        mainViewModel.chefId.observe(this){
//            mainViewModel.getChef(it)
//        }

        mainViewModel.newUser.observe(this) {
            Log.d("mainactivity", "newuser=$it")
            if (it) {
                navController.navigate(
                    MobileNavigationDirections.actionGlobalChefEditFragment(
                        EditPageType.CREATE_USER.index,
                        newUserProfile
                    )
                )
            } else {


                when (val mode = UserManger.readData("mode", this)) {
                    Mode.CHEF.index -> {
                        if (UserManger.user?.chefId != null) {
                            turnMode(mode)
                            navController.navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
                        } else {
                            turnMode(Mode.USER.index)
                            navController.navigate(MobileNavigationDirections.actionGlobalMenuFragment())
                        }
                    }
                    else -> {
                        turnMode(Mode.USER.index)
                        navController.navigate(MobileNavigationDirections.actionGlobalMenuFragment())
                    }

                }
            }
        }


        findNavController(R.id.nav_host_fragment_activity_main).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.menuFragment,
                R.id.likeFragment,
                R.id.orderManageFragment,
                R.id.chatFragment,
                R.id.userProfileFragment,
                R.id.calendar_set_open_radio,
                R.id.transactionFragment,
                R.id.chefFragment -> {
                    navView.visibility = View.VISIBLE
                    binding.toolbar6.visibility = View.GONE
                }
                R.id.menuDetailFragment,
                R.id.navigation_home,
                R.id.loginFragment,
                R.id.filterFragment->{
                    navView.visibility = View.GONE
                    binding.toolbar6.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.GONE
                    binding.toolbar6.visibility = View.VISIBLE
                    when(navController.currentDestination?.id){
                        R.id.bookFragment->{
                            binding.toolBarTitle.text = "確認訂單"
                        }
                        R.id.menuEditFragment->{
                            binding.toolBarTitle.text = "完成菜單"
                        }
                        R.id.calendarSetting->{
                            binding.toolBarTitle.text = "可訂狀態"
                        }
                        R.id.bookSetting->{
                            binding.toolBarTitle.text = "預訂設定"
                        }
                        R.id.orderDetailFragment->{
                            binding.toolBarTitle.text = "訂單資訊"
                        }
                        R.id.displayChefFragment->{
                            binding.toolBarTitle.text = "廚師資訊"
                        }
                        R.id.termsFragment->{
                            binding.toolBarTitle.text = "Terms of Use"
                        }
                        R.id.chatRoomFragment->{
                            binding.toolBarTitle.text = "聊天室"
                        }
                        else->{
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
        Log.d("mainactivity", "currentuser = $currentUser")
        updateUI(currentUser)

    }


    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            Log.d("mainactivity", "useravatar=${user.photoUrl}")
            val email = user.email
            val name = user.displayName
            val avatar = user.photoUrl.toString()

            if (name != null && email != null && avatar != null) {
                newUserProfile = ProfileInfo(name, email, avatar)
                mainViewModel.getUser(email)
            }
        }else{
            findNavController(R.id.nav_host_fragment_activity_main).navigate(MobileNavigationDirections.actionGlobalLoginFragment())
        }
    }

    fun turnMode(mode: Int) {
        UserManger.saveData(mode, this)
        if (mode == Mode.USER.index) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
        } else {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.chef_bottom_nav_menu)
        }
    }


    fun hideNaveView() {
        binding.navView.visibility = View.GONE
    }

    fun showNaveView() {
        binding.navView.visibility = View.VISIBLE
    }

    fun signIn() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signOut() {
        googleSignInClient.signOut()
        Firebase.auth.signOut()
        UserManger.saveData(Mode.LOGOUT.index, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("login info", data?.extras.toString())
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email
                val token = account?.idToken
                firebaseAuthWithGoogle(account.idToken!!)
                Log.i("givemepass", "email:$email, token:$token")
                Toast.makeText(this, "R.string.login_success", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Log.i("givemepass", "signInResult:failed code=" + e.message)
                Toast.makeText(this, "R.string.login_fail", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }



    fun block(userId:String,blockMenuList:List<String>?,blockReviewList: List<String>? ){

        if(blockMenuList!=null){
            mainViewModel.block(userId, blockMenuList, null)
        }

        if(blockReviewList!=null){
            mainViewModel.block(userId, null, blockReviewList)
        }
    }




    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
