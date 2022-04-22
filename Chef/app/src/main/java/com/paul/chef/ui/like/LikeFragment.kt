package com.paul.chef.ui.like

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
import com.paul.chef.ItemMenu
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.R
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.FragmentLikeBinding
import com.paul.chef.databinding.FragmentMenuListBinding
import com.paul.chef.ui.menu.MenuListAdapter
import com.paul.chef.ui.menu.MenuListViewModel
import java.util.*

class LikeFragment : Fragment(), ItemMenu {

    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!

    lateinit var likeViewModel:LikeViewModel

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeList = mutableListOf<String>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        likeViewModel =
            ViewModelProvider(this).get(LikeViewModel::class.java)



        //menuList recycler
        menuListAdapter = MenuListAdapter(this,likeViewModel )
        layoutManager = LinearLayoutManager(this.context)
        binding.likeRecycler.layoutManager = layoutManager
        binding.likeRecycler.adapter = menuListAdapter


        likeViewModel.likeList.observe(viewLifecycleOwner){
            Log.d("likefragment","it=$it")
            likeList.addAll(it)
//            if(it.isNotEmpty()){
                likeViewModel.getMenuList(it)
//            }

        }

        likeViewModel.menuList.observe(viewLifecycleOwner) {
            Log.d("likefragment","menulist=$it")
            menuListAdapter.submitList(it)
            menuListAdapter.notifyDataSetChanged()
        }




        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goDetail(menu: ChefMenu) {
        findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment(menu))
    }

    override fun like(menuId: String) {
        if(likeList.contains(menuId)){
            likeList.remove(menuId)
        }else{
            likeList.add(menuId)
        }
        likeViewModel.updateLikeList(likeList)
    }

}