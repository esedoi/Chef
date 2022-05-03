package com.paul.chef.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        homeViewModel.userData.observe(viewLifecycleOwner){
            mainViewModel.user = it
            (activity as MainActivity).turnMode(Mode.USER.index)
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
        }

        binding.signInButton.setOnClickListener {
            (activity as MainActivity).signIn()
        }

        binding.signOut.setOnClickListener {
            (activity as MainActivity).signOut()
        }


            binding.login.setOnClickListener{
                homeViewModel.login()
                (activity as MainActivity).turnMode(Mode.USER.index)
            }



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
