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
import com.google.android.material.chip.Chip
import com.paul.chef.*
import com.paul.chef.data.Menu
import com.paul.chef.databinding.FragmentMenuListBinding

class MenuListFragment : Fragment(), ItemMenu {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeIdList = mutableListOf<String>()

    lateinit var menuListViewModel:MenuListViewModel

    val menuList = mutableListOf<Menu>()

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
            val newMenuList = if(UserManger.user?.blockMenuList!=null){
                it.filter {itemMenu->
                    !UserManger.user?.blockMenuList!!.contains(itemMenu.id)
                }

            }else{
                it
            }

            menuList.addAll(newMenuList)
            menuListAdapter.submitList(newMenuList)
            menuListAdapter.notifyDataSetChanged()
        }

        menuListViewModel.likeIdList.observe(viewLifecycleOwner){
            likeIdList.clear()
            likeIdList.addAll(it)
        }

        val tagList = listOf<String>("素食","清真","魚","法式", "中式","日式","分子料理","有機", "無麩質")
        tagList.forEach {
            val chip = layoutInflater.inflate(R.layout.item_menu_list_chip,binding.menuListChipGroup , false) as Chip
            chip.text = it
            chip.id = tagList.indexOf(it)

            chip.setOnClickListener {
                    val chipIdList = binding.menuListChipGroup.checkedChipIds
                if(chipIdList.isEmpty()){
                    menuListAdapter.submitList(menuList)
                    menuListAdapter.notifyDataSetChanged()
                }else{
                    Log.d("menulistfragment", "chipidList=$chipIdList")
                    val chipTxtList = mutableListOf<String>()
                    chipIdList.forEach { id->
                        val text = binding.menuListChipGroup.findViewById<Chip>(id).text
                        chipTxtList.add(text.toString())
                    }
                    Log.d("menulistfragment", "chipTxtList=$chipTxtList")

                    val newMenuList = mutableListOf<Menu>()

                    if(menuList.isNotEmpty()){
                        for(menu in menuList){
                            if(menu.tagList?.containsAll(chipTxtList) == true){
                                newMenuList.add(menu)
                            }
                        }
                    }
                    Log.d("menulistfragment", "newMenuList=$newMenuList")
                    Log.d("menulistfragment", "menuList=$menuList")
                    menuListAdapter.submitList(newMenuList)
                    menuListAdapter.notifyDataSetChanged()
                }
            }
            binding.menuListChipGroup.addView(chip)
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

    override fun like(menuId:String) {
        if(likeIdList.contains(menuId)){
           likeIdList.remove(menuId)
        }else{
            likeIdList.add(menuId)
        }
        menuListViewModel.updateLikeList(likeIdList)
    }
}
