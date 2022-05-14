package com.paul.chef.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.paul.chef.*
import com.paul.chef.data.Address
import com.paul.chef.data.Menu
import com.paul.chef.databinding.FragmentMenuListBinding
import java.time.LocalDate

class MenuListFragment : Fragment(), ItemMenu {

    private var _binding: FragmentMenuListBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var likeIdList = mutableListOf<String>()

    lateinit var menuListViewModel: MenuListViewModel

    val menuList = mutableListOf<Menu>()
    val displayMenuList = mutableListOf<Menu>()

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
        menuListAdapter = MenuListAdapter(this, menuListViewModel, MenuType.FULL.index)
        layoutManager = LinearLayoutManager(this.context)
        binding.menuListRecycler.layoutManager = layoutManager
        binding.menuListRecycler.adapter = menuListAdapter

        val items =
            listOf(BookType.ChefSpace.userTxt, BookType.UserSpace.userTxt)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, items)
        (binding.menuListFilterLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)



        binding.menuListFilter.addTextChangedListener {
            Log.d("menulistfragment", "text = ${it.toString()}")

            val filterType = when (it.toString()) {
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
            if(filterType!=-1){
                menuListViewModel.getChefId(filterType)
            }
        }


        //default menuList
        menuListViewModel.menuList.observe(viewLifecycleOwner) {
            Log.d("menulistfragment", "observe it.size=${it.size}")
            val newMenuList = if (UserManger.user?.blockMenuList != null) {
                it.filter { itemMenu ->
                    !UserManger.user?.blockMenuList!!.contains(itemMenu.id)
                }
            } else {
                it
            }
            menuList.clear()
            menuList.addAll(newMenuList)
            displayMenuList.clear()
            displayMenuList.addAll(newMenuList)
            menuListAdapter.submitList(displayMenuList)
            menuListAdapter.notifyDataSetChanged()
        }


        val tagList = listOf<String>("素食", "清真", "魚", "法式", "中式", "日式", "分子料理", "有機", "無麩質")
        tagList.forEach {
            val chip = layoutInflater.inflate(
                R.layout.item_menu_list_chip,
                binding.menuListChipGroup,
                false
            ) as Chip
            chip.text = it
            chip.id = tagList.indexOf(it)
            chip.setOnCheckedChangeListener { compoundButton, b ->
                Log.d("menulistfragment", "compoundButton= $compoundButton")
                Log.d("menulistfragment", "b= $b")
            }

            chip.setOnCheckedChangeListener { compoundButton, b ->

                displayMenuList.clear()
                displayMenuList.addAll(menuList)

                val chipIdList = binding.menuListChipGroup.checkedChipIds
                if (chipIdList.isEmpty()) {
                    menuListAdapter.submitList(displayMenuList)
                    menuListAdapter.notifyDataSetChanged()
                } else {
                    Log.d("menulistfragment", "chipidList=$chipIdList")
                    val chipTxtList = mutableListOf<String>()
                    chipIdList.forEach { id ->
                        val text = binding.menuListChipGroup.findViewById<Chip>(id).text
                        chipTxtList.add(text.toString())
                    }

                    val newMenuList = mutableListOf<Menu>()

                    if (displayMenuList.isNotEmpty()) {
                        for (menu in displayMenuList) {
                            if (menu.tagList?.containsAll(chipTxtList) == true) {
                                newMenuList.add(menu)
                            }
                        }
                    }
                    Log.d("menulistfragment", "newMenuList.size=${newMenuList.size}")
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

        setFragmentResultListener("tagList") { requestKey, bundle ->
            val newList = bundle.getStringArrayList("filterTagList")
            Log.d("menulistfragment", "tagList = $newList")

            if (newList != null) {
                for (tag in newList) {
                    if(tagList.contains(tag)){
                        val chip = binding.menuListChipGroup.findViewById<Chip>(tagList.indexOf(tag))
                        if (chip.text == tag) {
                            chip.isChecked = true
                        }
                    }
                }
            }
        }

        menuListViewModel.likeIdList.observe(viewLifecycleOwner) {
            likeIdList.clear()
            likeIdList.addAll(it)
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
        if (likeIdList.contains(menuId)) {
            likeIdList.remove(menuId)
        } else {
            likeIdList.add(menuId)
        }
        menuListViewModel.updateLikeList(likeIdList)
    }
}
