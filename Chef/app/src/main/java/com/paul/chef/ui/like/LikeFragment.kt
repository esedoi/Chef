package com.paul.chef.ui.like

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.ItemMenu
import com.paul.chef.MenuType
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.Menu
import com.paul.chef.databinding.FragmentLikeBinding
import com.paul.chef.ui.menu.MenuListAdapter
import com.paul.chef.ui.menu.MenuListViewModel

class LikeFragment : Fragment(), ItemMenu {

    private var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!

    private val menuListViewModel: MenuListViewModel by activityViewModels()

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeList = mutableListOf<String>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //menuList recycler
        menuListAdapter = MenuListAdapter(this, menuListViewModel, MenuType.FULL.index)
        layoutManager = LinearLayoutManager(this.context)
        binding.likeRecycler.layoutManager = layoutManager
        binding.likeRecycler.adapter = menuListAdapter


        menuListViewModel.likeIdList.observe(viewLifecycleOwner) {

            likeList.clear()
            likeList.addAll(it)
            menuListViewModel.getLikeList(it)
        }

        menuListViewModel.likeList.observe(viewLifecycleOwner) {

            if (it.isEmpty()) {
                binding.chatUserEmptyImg.visibility = View.VISIBLE
                binding.chatEmptyTxt.visibility = View.VISIBLE
            } else {
                binding.chatUserEmptyImg.visibility = View.GONE
                binding.chatEmptyTxt.visibility = View.GONE
            }
            menuListAdapter.submitList(it)
            menuListAdapter.notifyDataSetChanged()
        }


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goDetail(menu: Menu) {
        findNavController().navigate(MobileNavigationDirections.actionGlobalMenuDetailFragment(menu))
    }

    override fun like(menuId: String) {
        if (likeList.contains(menuId)) {
            likeList.remove(menuId)
        } else {
            likeList.add(menuId)
        }
        menuListViewModel.updateLikeList(likeList)
    }

}