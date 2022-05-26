package com.paul.chef.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menuDetail.bindImage

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val userProfileViewModel by viewModels<UserProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val user = UserManger.user

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
            userProfileViewModel.getChef(it)
        }
        userProfileViewModel.getChefDone.observe(viewLifecycleOwner) {
            (activity as MainActivity).turnMode(Mode.CHEF.index)
            findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
        }

        if (user.chefId != null) {
            binding.userProfileCreateChef.text = getString(R.string.turn_chef_mode)
            binding.userProfileCreateChef.setOnClickListener {
                (activity as MainActivity).turnMode(Mode.CHEF.index)
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalOrderManageFragment()
                )
            }
        } else {
            binding.userProfileCreateChef.text = getString(R.string.become_chef)
            binding.userProfileCreateChef.setOnClickListener {
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
