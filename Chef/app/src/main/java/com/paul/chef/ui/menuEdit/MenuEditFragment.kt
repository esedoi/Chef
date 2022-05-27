package com.paul.chef.ui.menuEdit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.paul.chef.*
import com.paul.chef.data.Discount
import com.paul.chef.data.Dish
import com.paul.chef.databinding.FragmentMenuEditBinding
import com.paul.chef.databinding.ItemAddDishBinding
import com.paul.chef.databinding.ItemDishOptionalBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menuDetail.DetailImagesAdapter
import timber.log.Timber

class MenuEditFragment : Fragment(), AddDiscount, MenuEditImg {

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private var _dishTypeView: ItemAddDishBinding? = null
    private val dishTypeView get() = _dishTypeView!!

    private var _dishView: ItemDishOptionalBinding? = null
    private val dishView get() = _dishView!!

    var bindingList = mutableListOf<ItemAddDishBinding>()
    private var allDishBindingList = mutableListOf<ItemDishOptionalBinding>()
    var pendingList = mutableListOf<Dish>()

    private lateinit var discountAdapter: DiscountAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var imageAdapter: DetailImagesAdapter

    var discountList = mutableListOf<Discount>()

    private val arg: MenuEditFragmentArgs by navArgs()

    private var imgList = mutableListOf<String>()
    var tagList = mutableListOf<String>()

    private var bindingItemCountList = mutableListOf<Int>()
    private var bindingItemCount = 0

    var openBoolean: Boolean = false

    private val menuEditViewModel by viewModels<MenuEditViewModel> { getVmFactory() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // discount recycler
        discountAdapter = DiscountAdapter(this)
        layoutManager = LinearLayoutManager(this.context)
        binding.discountRecycler.layoutManager = layoutManager
        binding.discountRecycler.adapter = discountAdapter

        // imagesRecyclerView
        imageAdapter = DetailImagesAdapter(ImgRecyclerType.IMAGE_EDIT.index, this, null, null)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.menuEditImgRecycler.layoutManager = layoutManager
        binding.menuEditImgRecycler.adapter = imageAdapter

        binding.menuEditAddImg.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalImageUploadFragment(ImgType.MENU.index)
            )
        }
        setFragmentResultListener("requestImg") { _, bundle ->
            val result = bundle.getString("downloadUri")
            if (result != null) {
                imgList.add(result)
                imageAdapter.submitList(imgList)
                imageAdapter.notifyDataSetChanged()
            }
        }

        val items =
            listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, items)
        (binding.menuEditDiscountPeople.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // menuEdit
//        val editMenu = arg.menu
//        if (editMenu != null) {
//            binding.menuEditName.editText?.setText(editMenu.menuName)
//            binding.menuEditPerprice.editText?.setText(editMenu.perPrice)
//            binding.menuEditIntro.editText?.setText(editMenu.intro)
//            discountAdapter.submitList(editMenu.discount)
//            imageAdapter.submitList(editMenu.images)
//
//            for (i in 0..editMenu.dishes.last().typeNumber) {
//
//                val typeList = editMenu.dishes.filter {
//                    it.typeNumber == i
//                }
//                addDish(container, typeList[0].option, typeList)
//            }
//        }

        binding.addDiscountBtn.setOnClickListener {
            when {
                binding.menuEditDiscountPeople.editText?.text.toString() == "" -> {
                    Toast.makeText(
                        this.context,
                        getString(R.string.please_select_people),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.menuEditDiscountPercentOff.editText?.text.toString() == "" -> {
                    Toast.makeText(
                        this.context,
                        getString(R.string.please_enter_discount),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val people = binding.menuEditDiscountPeople.editText?.text.toString().toInt()
                    val percentOff =
                        binding.menuEditDiscountPercentOff.editText?.text.toString().toInt()
                    val discount = Discount(people, percentOff)
                    discountList.add(discount)
                    discountAdapter.submitList(discountList)
                    discountAdapter.notifyDataSetChanged()
                    binding.menuEditDiscountPercentOff.editText?.text?.clear()
                    binding.menuEditDiscountPeople.editText?.text?.clear()
                }
            }
        }

        binding.fixedBtn.setOnClickListener {
            addDish(container, AddDishType.FIXED.index, null)
        }

        binding.optionalBtn.setOnClickListener {
            addDish(container, AddDishType.OPTIONAL.index, null)
        }

        binding.menuEditAddTagBtn.setOnClickListener {
            binding.menuEditTagsGroup.removeAllViews()
            findNavController().navigate(MobileNavigationDirections.actionGlobalAddTagFragment())
        }

        setFragmentResultListener("tagList") { _, bundle ->
            val newList = bundle.getStringArrayList("tagList")
            if (newList != null) {
                tagList.clear()
                tagList.addAll(newList)
                tagList.forEach {
                    val chip = Chip(this.context)
                    chip.text = it
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener { view ->
                        binding.menuEditTagsGroup.removeView(view)
                    }
                    binding.menuEditTagsGroup.addView(chip)
                }
            }
        }

        menuEditViewModel.openBoolean.observe(viewLifecycleOwner) {
            openBoolean = it
        }

        // create Menu
        binding.build.setOnClickListener {
            Timber.d("bindingItemCountList = $bindingItemCountList")
            Timber.d("pendingList.size = ${pendingList.size}")
            Timber.d("allDishBindingList.size = ${allDishBindingList.size}")


            val dishList: List<Dish> = getFinalDishList()

            if (errorHandle(dishList)) {
                val menuName = binding.menuEditName.editText?.text.toString()
                val menuIntro = binding.menuEditIntro.editText?.text.toString()
                val perPrice = binding.menuEditPerprice.editText?.text.toString().toInt()

                menuEditViewModel.createMenu(
                    menuName,
                    menuIntro,
                    perPrice,
                    imgList,
                    discountList,
                    dishList,
                    tagList,
                    openBoolean
                )
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalDoneFragment("chefPage")
                )
            }
        }

        return root
    }

    private fun getFinalDishList(): List<Dish> {
        val dishList = mutableListOf<Dish>()
        var typeNumber: Int

        for (i in allDishBindingList) {
            val price = if (i.menuDishPriceInput.editText?.text.toString() == "") {
                0
            } else {
                i.menuDishPriceInput.editText?.text.toString().toInt()
            }
            val dishName = i.menuDishNameInput.editText?.text.toString()
            val type = pendingList[allDishBindingList.indexOf(i)].type
            val option = pendingList[allDishBindingList.indexOf(i)].option
            typeNumber = pendingList[allDishBindingList.indexOf(i)].typeNumber
            val dish = Dish(type, option, dishName, price, typeNumber)
            dishList.add(dish)
        }
        return dishList
    }

    private fun errorHandle(dishList: List<Dish>): Boolean {
        return if (
            binding.menuEditName.editText?.text.toString() == "" ||
            binding.menuEditIntro.editText?.text.toString() == "" ||
            binding.menuEditPerprice.editText?.text.toString() == ""
        ) {
            Toast.makeText(
                this.context,
                getString(R.string.field_not_completet),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (!checkIsNameFilled(allDishBindingList)) {
            Toast.makeText(
                this.context,
                getString(R.string.please_complete_dish_name),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (imgList.isEmpty()) {
            Toast.makeText(
                this.context,
                getString(R.string.toast_please_upload_photo),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (dishList.isNullOrEmpty()) {
            Toast.makeText(this.context, getString(R.string.please_add_dish), Toast.LENGTH_SHORT)
                .show()
            false
        } else {
            true
        }
    }

    private fun checkIsNameFilled(allDishBindingList: MutableList<ItemDishOptionalBinding>): Boolean {
        var isNameFilled = true

        for (i in allDishBindingList) {
            if (i.menuDishNameInput.editText?.text.toString() == "") {
                isNameFilled = false
            }
        }
        return isNameFilled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun add(people: Int, percentOff: Int) {
        val discount = Discount(people, percentOff)
        discountList.add(discount)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun remove(position: Int, percentOff: Int) {
        discountList.removeAt(position)
        discountAdapter.submitList(discountList)
        discountAdapter.notifyDataSetChanged()
    }

    private fun addDish(container: ViewGroup?, option: Int, displayTypeList: List<Dish>?) {
        _dishTypeView =
            ItemAddDishBinding.inflate(LayoutInflater.from(context), container, false)
        bindingList.add(dishTypeView)

        setDishTypeIntroText(option)

        for (itemBindingList in bindingList) {
            setDishTypeSelector()
            var typeIndex: Int

            itemBindingList.removeType.setOnClickListener {
                typeIndex = bindingList.indexOf(itemBindingList)
                binding.dishTypeLinear.removeView(bindingList[typeIndex].root)
                bindingList.removeAt(typeIndex)

                val beforeDishNum: Int = getBeforeDishNum(typeIndex, bindingItemCountList)
                val numberInType = bindingItemCountList[typeIndex]

                removeList(beforeDishNum, numberInType, pendingList, allDishBindingList)
                bindingItemCountList.removeAt(typeIndex)
            }

            itemBindingList.addDish.setOnClickListener {
                typeIndex = bindingList.indexOf(itemBindingList)
                _dishView =
                    ItemDishOptionalBinding.inflate(LayoutInflater.from(context), container, false)
                setPriceInputVisibility(option)
                val dishBindingList = mutableListOf<ItemDishOptionalBinding>()
                dishBindingList.add(dishView)
                dishView.menuDishNameInput.dispatchSetSelected(true)

                for (itemDishBindingList in dishBindingList) {
                    val dishType = dishTypeView.menuAddDishType.editText?.text.toString()
                    val pendingDish = Dish(dishType, option, typeNumber = typeIndex)
                    pendingList.add(pendingDish)
                    allDishBindingList.add(itemDishBindingList)

                    itemDishBindingList.dishRemove.setOnClickListener {
                        val dishIndex = dishBindingList.indexOf(itemDishBindingList)
                        typeIndex = bindingList.indexOf(itemBindingList)
                        bindingList[typeIndex].dishLinear.removeView(
                            dishBindingList[dishIndex].root
                        )
                        dishBindingList.removeAt(dishIndex)
                        allDishBindingList.remove(itemDishBindingList)
                        pendingList.remove(pendingDish)
                        bindingItemCountList[typeIndex]--
                    }
                }
                bindingList[typeIndex].dishLinear.addView(dishView.root)
                bindingItemCountList[typeIndex]++
            }
        }
        binding.dishTypeLinear.addView(dishTypeView.root)
        bindingItemCountList.add(bindingItemCount)
    }

    private fun getBeforeDishNum(typeIndex: Int, bindingItemCountList: MutableList<Int>): Int {
        var beforeDishNum = 0
        var counter = typeIndex
        for (i in bindingItemCountList) {
            if (counter > 0) {
                beforeDishNum += i
                counter--
            }
        }
        return beforeDishNum
    }

    private fun setPriceInputVisibility(option: Int) {
        if (option == AddDishType.FIXED.index) {
            dishView.menuDishPriceInput.visibility = View.GONE
        }
    }

    private fun setDishTypeSelector() {
        val typeList =
            listOf("開胃菜", "湯", "飲料", "酒", "前菜", "主菜", "甜點", "其他")

        val dishTypeAdapter =
            ArrayAdapter(requireContext(), R.layout.list_people_item, typeList)
        (dishTypeView.menuAddDishType.editText as? AutoCompleteTextView)?.setAdapter(
            dishTypeAdapter
        )
    }

    private fun setDishTypeIntroText(option: Int) {
        if (option == AddDishType.FIXED.index) {
            dishTypeView.introText.text = getString(R.string.fixed_type_intro_txt)
        } else {
            dishTypeView.introText.text = getString(R.string.optional_type_intro_txt)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun remove(position: Int) {
        imgList.removeAt(position)
        imageAdapter.submitList(imgList)
        imageAdapter.notifyDataSetChanged()
    }

    private fun removeList(
        beforeDishNum: Int,
        numberInType: Int,
        pendingList: MutableList<Dish>,
        allDishBindingList: MutableList<ItemDishOptionalBinding>
    ) {
        if (numberInType < 1) return
        pendingList.removeAt(beforeDishNum)
        allDishBindingList.removeAt(beforeDishNum)
        removeList(beforeDishNum, numberInType - 1, pendingList, allDishBindingList)
    }
}
