package com.paul.chef.ui.chef

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.*
import com.paul.chef.databinding.FragmentChefPageBinding

class ChefFragment : Fragment() {

    private var _binding: FragmentChefPageBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chefViewModel =
            ViewModelProvider(this).get(ChefViewModel::class.java)


        _binding = FragmentChefPageBinding.inflate(inflater, container, false)
        val root: View = binding.root


        chefViewModel.chefInfo.observe(viewLifecycleOwner){
            Log.d("CHEFFRAGMENT","接收到資料")
            binding.chefName.text = it.profileInfo.name
            binding.chefIntro.text = it.profileInfo.introduce
        }

        binding.createMenu.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuEditFragment(null))
        }

        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalChefEditFragment())
        }

        binding.bookSettingBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalBookSetting())
        }

        binding.turnToUser.setOnClickListener {
            (activity as MainActivity).turnMode(Mode.USER.index)
            UserManger().mode = Mode.USER.index
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
