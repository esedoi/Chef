package com.paul.chef.ui.userProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.ui.chef.ChefViewModel
import com.paul.chef.ui.orderDetail.OrderDetailViewModel

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userProfileViewModel =
            ViewModelProvider(this).get(UserProfileViewModel::class.java)

        val user = mainViewModel.user
        Log.d("userprofilefragment"," user= $user")
        if (user != null) {
            if(user.chefId!=null){
                findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
            }
        }


//        binding.createUser.setOnClickListener {
//            val userName = "Amy"
//            val userEmail = "amy@gmail.com"
//            val userIntro = "i am a nice guest"
//            userProfileViewModel.createUser(userEmail,userIntro, userName)
//        }

        userProfileViewModel.chefId.observe(viewLifecycleOwner){
            Log.d("userprofilefragment","++++++++++++chefid = $it")
            mainViewModel.getChef(it)
            (activity as MainActivity).turnMode(Mode.CHEF.index)
            findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
        }

        binding.userProfileCreateChef.setOnClickListener {
            if(mainViewModel.user!=null){
                val name = mainViewModel.user!!.profileInfo.name
                val intro = mainViewModel.user!!.profileInfo.introduce
                val email = mainViewModel.user!!.profileInfo.email
                val avatar = mainViewModel.user!!.profileInfo.avatar
                userProfileViewModel.createChef(name, intro, email, avatar)
//                (activity as MainActivity).turnMode(Mode.CHEF.index)
//                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
        }

//        binding.turnToChef.setOnClickListener {
//            (activity as MainActivity).turnMode(Mode.CHEF.index)
//            findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
//        }
        return  root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}