package com.paul.chef.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            editChef.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalChefEditFragment())
            }
            chef.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
            }
            menu.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
            }
            menuDetail.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment())
            }
            menuEdit.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalMenuEditFragment())
            }
            book.setOnClickListener {
                findNavController().navigate(MobileNavigationDirections.actionGlobalBookFragment())
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
