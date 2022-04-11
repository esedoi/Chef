package com.paul.chef.ui.chef

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.MobileNavigationDirections
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

        val chefId = "9qKTEyvYbiXXEJSjDJGF"
        db.collection("Chef")
            .whereEqualTo("chefId", chefId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!.documents) {
                    val item = doc.data
                    Log.d("chefeditfragment", "item=$item")
                }
            }

        binding.createMenu.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuEditFragment())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
