package com.paul.chef.ui.chefEdit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.databinding.FragmentChefEditBinding

class ChefEditFragment : Fragment() {

    private var _binding: FragmentChefEditBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chefEditViewModel =
            ViewModelProvider(this).get(ChefEditViewModel::class.java)

        _binding = FragmentChefEditBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val chefId = "wbdfKqWPi0SDOL7bWA6G"






        binding.chefSave.setOnClickListener {
            val name = binding.chefNameEdit.text.toString()
            val email = binding.chefEmailEdit.text.toString()
            val intro = binding.chefIntroEdit.text.toString()
            if (name != "" && email != "" && intro != "") {
                chefEditViewModel.saveChef(chefId, name, email, intro)
            } else {
                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
            }
        }


        binding.createChef.setOnClickListener {
            Log.d("chefeditfragment", "binding.chefNameEdit.text=${binding.chefNameEdit.text}")
            val name = binding.chefNameEdit.text.toString()
            val email = binding.chefEmailEdit.text.toString()
            val intro = binding.chefIntroEdit.text.toString()
            if (name != "" && email != "" && intro != "") {
                chefEditViewModel.createChef(name, email, intro)
            } else {
                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
