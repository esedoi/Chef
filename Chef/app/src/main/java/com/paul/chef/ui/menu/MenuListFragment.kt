package com.paul.chef.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.FragmentMenuListBinding

class MenuListFragment : Fragment(), ItemMenu {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeIdList = mutableListOf<String>()

    lateinit var menuListViewModel:MenuListViewModel



    val menuList = mutableListOf<ChefMenu>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        val root: View = binding.root

         menuListViewModel =
            ViewModelProvider(this).get(MenuListViewModel::class.java)




        //menuList recycler
        menuListAdapter = MenuListAdapter(this,menuListViewModel,MenuType.FULL.index)
        layoutManager = LinearLayoutManager(this.context)
        binding.menuListRecycler.layoutManager = layoutManager
        binding.menuListRecycler.adapter = menuListAdapter


        menuListViewModel.menuList.observe(viewLifecycleOwner) {
            menuListAdapter.submitList(it)
            menuListAdapter.notifyDataSetChanged()
        }

        menuListViewModel.likeIdList.observe(viewLifecycleOwner){
            likeIdList.clear()
            likeIdList.addAll(it)
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

    override fun like(menuId:String) {
        if(likeIdList.contains(menuId)){
           likeIdList.remove(menuId)
        }else{
            likeIdList.add(menuId)
        }
        menuListViewModel.updateLikeList(likeIdList)
    }
}
