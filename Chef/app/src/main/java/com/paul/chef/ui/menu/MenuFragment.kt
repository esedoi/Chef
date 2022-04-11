package com.paul.chef.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.FragmentMenuListBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    val menuList = mutableListOf<ChefMenu>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val chefId = "9qKTEyvYbiXXEJSjDJGF"
        db.collection("Menu")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, ChefMenu::class.java)
                    menuList.add(data)
                    Log.d("menufragment", "item=$item")
                }
            }

       binding.goMenuDetail.setOnClickListener {
          val menu = menuList[0]
           findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment(menu))

       }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
