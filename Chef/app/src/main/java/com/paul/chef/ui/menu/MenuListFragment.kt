package com.paul.chef.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.MenuDetail
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.FragmentMenuListBinding
import com.paul.chef.ui.chefEdit.ChefEditViewModel
import com.paul.chef.ui.menuEdit.DiscountAdapter

class MenuListFragment : Fragment(), MenuDetail {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null


    val menuList = mutableListOf<ChefMenu>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuListViewModel =
            ViewModelProvider(this).get(MenuListViewModel::class.java)

        val chefId = "9qKTEyvYbiXXEJSjDJGF"



        //menuList recycler
        menuListAdapter = MenuListAdapter(this)
        layoutManager = LinearLayoutManager(this.context)
        binding.menuListRecycler.layoutManager = layoutManager
        binding.menuListRecycler.adapter = menuListAdapter

        //觀察 howmuch live data
        menuListViewModel.menuList.observe(viewLifecycleOwner) {
            menuListAdapter.submitList(it)
            menuListAdapter.notifyDataSetChanged()
        }



//        binding.goMenuDetail.setOnClickListener {
//          val menu = menuList[0]
//           findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment(menu))
//
//       }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goDetail(menu: ChefMenu) {
        findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment(menu))
    }
}
