package com.paul.chef.ui.menuDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.databinding.FragmentMenuDetailBinding

class MenuDetailFragment : Fragment() {

    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

    //safe args
    private val arg: MenuDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //navigation safe args
        val menu = arg.chefMenu


        binding.choice.setOnClickListener {
            Log.d("menudetailfragment", "menu=$menu")
            findNavController().navigate(MobileNavigationDirections.actionGlobalBookFragment(menu))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
