package com.paul.chef.ui.userProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.ui.menuDetail.bindImage


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

        val user = UserManger.user
        Log.d("userprofilefragment", " user= $user")

        binding.userProfileIntro.text = user?.profileInfo?.introduce!!
        binding.userProfileNameText.text = user.profileInfo.name

        bindImage(binding.userProfileImg, user.profileInfo.avatar)
        val outlineProvider = ProfileOutlineProvider()
        binding.userProfileImg.outlineProvider = outlineProvider
        binding.userProfileLogout.setOnClickListener {
            (activity as MainActivity).signOut()
            findNavController().navigate(MobileNavigationDirections.actionGlobalLoginFragment())
        }

        userProfileViewModel.chefId.observe(viewLifecycleOwner) {
            Log.d("userprofilefragment", "++++++++++++chefid = $it")
            userProfileViewModel.getChef(it)
        }
        userProfileViewModel.getChefDone.observe(viewLifecycleOwner) {
            if (it) {
                (activity as MainActivity).turnMode(Mode.CHEF.index)
                findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
            }
        }


        if (user.chefId != null) {
            binding.userProfileCreateChef.text = "切換廚師模式"
            binding.userProfileCreateChef.setOnClickListener {
                (activity as MainActivity).turnMode(Mode.CHEF.index)
                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
        } else {
            binding.userProfileCreateChef.text = "成為廚師"
            binding.userProfileCreateChef.setOnClickListener {
                Log.d("userprofilemanager", "user=$user")
                userProfileViewModel.createChef(user)
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}