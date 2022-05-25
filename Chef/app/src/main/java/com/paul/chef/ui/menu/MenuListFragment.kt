package com.paul.chef.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.paul.chef.*
import com.paul.chef.data.Menu
import com.paul.chef.databinding.FragmentMenuListBinding
import com.paul.chef.ext.getVmFactory

class MenuListFragment : Fragment(), ItemMenu {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeIdList = mutableListOf<String>()

    private val menuListViewModel by viewModels<MenuListViewModel> { getVmFactory() }

    val menuList = mutableListOf<Menu>()
    private val displayMenuList = mutableListOf<Menu>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // menuList recycler
        menuListAdapter = MenuListAdapter(this, menuListViewModel, MenuType.FULL.index)
        layoutManager = LinearLayoutManager(this.context)
        binding.menuListRecycler.layoutManager = layoutManager
        binding.menuListRecycler.adapter = menuListAdapter

        val items =
            listOf(BookType.ChefSpace.userTxt, BookType.UserSpace.userTxt)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, items)
        (binding.menuListFilterLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.menuListFilter.addTextChangedListener {
            val filterType: Int = getFilterType(it.toString())
            if (filterType != -1) {
                menuListViewModel.getChefId(filterType)
            }
        }

        // default menuList
        menuListViewModel.menuList.observe(viewLifecycleOwner) {
            val newMenuList: List<Menu> = filterBlockMenuList(it)
            menuList.clear()
            menuList.addAll(newMenuList)
            displayMenuList.clear()
            displayMenuList.addAll(newMenuList)
            menuListAdapter.submitList(displayMenuList)
            menuListAdapter.notifyDataSetChanged()
        }

        val tagList = resources.getStringArray(R.array.menu_tag_list).toList()
        tagList.forEach {
            val chip = layoutInflater.inflate(
                R.layout.item_menu_list_chip,
                binding.menuListChipGroup,
                false
            ) as Chip
            chip.text = it
            chip.id = tagList.indexOf(it)

            chip.setOnCheckedChangeListener { _, _ ->

                displayMenuList.clear()
                displayMenuList.addAll(menuList)

                val chipIdList = binding.menuListChipGroup.checkedChipIds
                if (chipIdList.isEmpty()) {
                    menuListAdapter.submitList(displayMenuList)
                    menuListAdapter.notifyDataSetChanged()
                } else {
                    val chipTxtList: List<String> = createChipTxtList(chipIdList)
                    val newMenuList: List<Menu> = sortNewMenuList(chipTxtList, displayMenuList)
                    displayMenuList.clear()
                    displayMenuList.addAll(newMenuList)
                    menuListAdapter.submitList(displayMenuList)
                    menuListAdapter.notifyDataSetChanged()
                }
            }
            binding.menuListChipGroup.addView(chip)
        }

        binding.menuListTagFilterBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalAddTagFragment())
        }

        setFragmentResultListener("tagList") { _, bundle ->
            val newList = bundle.getStringArrayList("filterTagList")

            if (newList != null) {
                modifyChipCheck(newList, tagList)
            }
        }

        menuListViewModel.liveUser.observe(viewLifecycleOwner) {
            menuListViewModel.filterLikeIdList(it)
        }
        menuListViewModel.likeIdList.observe(viewLifecycleOwner) {
            likeIdList.clear()
            likeIdList.addAll(it)
        }

        return root
    }

    private fun getFilterType(menuListFilter: String): Int {
        return when (menuListFilter) {
            BookType.ChefSpace.userTxt -> {
                BookType.ChefSpace.index
            }
            BookType.UserSpace.userTxt -> {
                BookType.UserSpace.index
            }
            else -> {
                -1
            }
        }
    }

    private fun filterBlockMenuList(menuList: List<Menu>): List<Menu> {
        return if (UserManger.user?.blockMenuList != null) {
            menuList.filter { itemMenu ->
                !UserManger.user?.blockMenuList!!.contains(itemMenu.id)
            }
        } else {
            menuList
        }
    }

    private fun sortNewMenuList(
        chipTxtList: List<String>,
        displayMenuList: MutableList<Menu>
    ): List<Menu> {
        val newMenuList = mutableListOf<Menu>()
        if (displayMenuList.isNotEmpty()) {
            for (menu in displayMenuList) {
                if (menu.tagList?.containsAll(chipTxtList) == true) {
                    newMenuList.add(menu)
                }
            }
        }
        return newMenuList
    }

    private fun createChipTxtList(chipIdList: List<Int>): List<String> {
        val chipTxtList = mutableListOf<String>()
        chipIdList.forEach { id ->
            val text = binding.menuListChipGroup.findViewById<Chip>(id).text
            chipTxtList.add(text.toString())
        }
        return chipTxtList
    }

    private fun modifyChipCheck(newList: ArrayList<String>, tagList: List<String>) {
        for (tag in newList) {
            if (tagList.contains(tag)) {
                val chip = binding.menuListChipGroup.findViewById<Chip>(tagList.indexOf(tag))
                if (chip.text == tag) {
                    chip.isChecked = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goDetail(menu: Menu) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalMenuDetailFragment(menu)
        )
    }

    override fun like(menuId: String) {
        if (likeIdList.contains(menuId)) {
            likeIdList.remove(menuId)
        } else {
            likeIdList.add(menuId)
        }
        menuListViewModel.updateLikeList(likeIdList)
    }
}
