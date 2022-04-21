package com.paul.chef.ui.userProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.turnToChef.setOnClickListener {
            (activity as MainActivity).turnMode(Mode.CHEF.index)
            findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
        }
        return  root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}