package com.paul.chef.ui.menuEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.databinding.FragmentMenuEditBinding
import com.paul.chef.ui.chefEdit.ChefEditViewModel

class MenuEditFragment : Fragment() {

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuEditViewModel =
            ViewModelProvider(this).get(MenuEditViewModel::class.java)


        binding.add.setOnClickListener {
            menuEditViewModel.createMenu()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
