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
import androidx.navigation.fragment.navArgs
import com.paul.chef.*
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.ui.bottomSheetPicker.PickerBottomSheetArgs
import com.paul.chef.ui.chef.ChefViewModel
import com.paul.chef.ui.menuDetail.bindImage
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

        val user = UserManger.user
        Log.d("userprofilefragment"," user= $user")

        binding.userProfileIntro.text = user?.profileInfo?.introduce!!
        binding.userProfileNameText.text = user.profileInfo.name

        bindImage(binding.userProfileImg, user.profileInfo.avatar)
        val outlineProvider = ProfileOutlineProvider()
        binding.userProfileImg.outlineProvider = outlineProvider
        binding.userProfileLogout.setOnClickListener {
            (activity as MainActivity).signOut()
            findNavController().navigate(MobileNavigationDirections.actionGlobalNavigationHome())
        }


            if(user.chefId!=null){
                findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
            }


        userProfileViewModel.chefId.observe(viewLifecycleOwner){
            Log.d("userprofilefragment","++++++++++++chefid = $it")
            userProfileViewModel.getChef(it)
        }
        userProfileViewModel.getChefDone.observe(viewLifecycleOwner){
            if(it){
                (activity as MainActivity).turnMode(Mode.CHEF.index)
            }
        }

        binding.userProfileCreateChef.setOnClickListener {
            Log.d("userprofilemanager", "user=$user")
            userProfileViewModel.createChef(user)
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