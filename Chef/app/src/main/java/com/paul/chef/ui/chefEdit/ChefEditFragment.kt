package com.paul.chef.ui.chefEdit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



        //接收廚師資訊
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

        binding.chefSave.setOnClickListener {
            chefEditViewModel.saveChef(chefId)
        }

        binding.createChef.setOnClickListener {
            chefEditViewModel.createChef()
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
