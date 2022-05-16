package com.paul.chef.ui.menuEdit


import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
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
import com.paul.chef.ui.menuDetail.DetailImagesAdapter
import com.paul.chef.ui.menuDetail.bindImage
import kotlin.math.atan2


class MenuEditFragment : Fragment(), AddDiscount, MenuEditImg {

    private var _binding: FragmentMenuEditBinding? = null
    private val binding get() = _binding!!

    private var _dishTypeView: ItemAddDishBinding? = null
    private val dishTypeView get() = _dishTypeView!!

    private var _dishView: ItemDishOptionalBinding? = null
    private val dishView get() = _dishView!!


    var bindingList = mutableListOf<ItemAddDishBinding>()
    var allDishBindingList = mutableListOf<ItemDishOptionalBinding>()
    var pendingList = mutableListOf<Dish>()

    var menuName = ""
    var menuIntro = ""
    var perPrice = -1
    var typeNumber = -1


    private lateinit var discountAdapter: DiscountAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var imageAdapter: DetailImagesAdapter


    var discountList = mutableListOf<Discount>()
    var people: Int = 0

    var dishType: String = ""

    var dishList = mutableListOf<Dish>()

    private val arg: MenuEditFragmentArgs by navArgs()

    var imgList = mutableListOf<String>()
    var tagList = mutableListOf<String>()


    var bindingItemCountList = mutableListOf<Int>()
    var bindingItemCount = 0

   var openBoolean: Boolean = false


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuEditViewModel =
            ViewModelProvider(this).get(MenuEditViewModel::class.java)


        //discount recycler
        discountAdapter = DiscountAdapter(this)
        layoutManager = LinearLayoutManager(this.context)
        binding.discountRecycler.layoutManager = layoutManager
        binding.discountRecycler.adapter = discountAdapter

        //imagesRecyclerView
        imageAdapter = DetailImagesAdapter(ImgRecyclerType.IMAGE_EDIT.index, this, null, null)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.menuEditImgRecycler.layoutManager = layoutManager
        binding.menuEditImgRecycler.adapter = imageAdapter


        binding.menuEditAddImg.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalImageUploadFragment(
                    ImgType.MENU.index
                )
            )
        }
        setFragmentResultListener("requestImg") { requestKey, bundle ->
            val result = bundle.getString("downloadUri")
            Log.d("chefeditfragment", "result=$result")
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

        val editMenu = arg.menu
        if (editMenu != null) {
            binding.menuEditName.editText?.setText(editMenu.menuName)
            binding.menuEditPerprice.editText?.setText(editMenu.perPrice)
            binding.menuEditIntro.editText?.setText(editMenu.intro)
            discountAdapter.submitList(editMenu.discount)
            imageAdapter.submitList(editMenu.images)




            for(i in 0..editMenu.dishes.last().typeNumber){
                val typeList = editMenu.dishes.filter {
                    it.typeNumber==i
                }
                Log.d("menueditfragment", "$typeList")
                addDish(container,typeList[0].option, typeList )
            }

        } else {

        }


        binding.addDiscountBtn.setOnClickListener {
            if (binding.menuEditDiscountPeople.editText?.text.toString() != "") {
                people = binding.menuEditDiscountPeople.editText?.text.toString().toInt()
                if (binding.menuEditDiscountPercentOff.editText?.text.toString() != "") {
                    val percentOff =
                        binding.menuEditDiscountPercentOff.editText?.text.toString().toInt()
                    val discount = Discount(people, percentOff)
                    discountList.add(discount)
                    discountAdapter.submitList(discountList)
                    discountAdapter.notifyDataSetChanged()
                    binding.menuEditDiscountPercentOff.editText?.text?.clear()
                    binding.menuEditDiscountPeople.editText?.text?.clear()

                } else {
                    Toast.makeText(this.context, "請填寫折扣", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this.context, "請選擇人數", Toast.LENGTH_SHORT).show()
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

        setFragmentResultListener("tagList") { requestKey, bundle ->
            val newList = bundle.getStringArrayList("tagList")
            Log.d("menuEditfragment", "tagList = $tagList")
            if (newList != null) {
                tagList.clear()
                tagList.addAll(newList)
                tagList.forEach { it ->
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

        menuEditViewModel.openBoolean.observe(viewLifecycleOwner){
            openBoolean = it
        }


        //create Menu
        binding.build.setOnClickListener {
            Log.d("menuEditfragment", "bindingItemCountList = ${bindingItemCountList}")
            Log.d("menuEditfragment", "pendingList.size = ${pendingList.size}")
            Log.d("menuEditfragment", "allDishBindingList.size = ${allDishBindingList.size}")

            if (binding.menuEditName.editText?.text.toString() == "" || binding.menuEditIntro.editText?.text.toString() == "" || binding.menuEditPerprice.editText?.text.toString() == "") {
                Toast.makeText(this.context, "欄位未完成", Toast.LENGTH_SHORT).show()
            } else {

                menuName = binding.menuEditName.editText?.text.toString()
                menuIntro = binding.menuEditIntro.editText?.text.toString()
                perPrice = binding.menuEditPerprice.editText?.text.toString().toInt()

                var count = 0
                var isNameFilled = true


                for (i in allDishBindingList) {

                    if (i.menuDishNameInput.editText?.text.toString() == "") {
                        isNameFilled = false
                    } else {
                        val price = if (i.menuDishPriceInput.editText?.text.toString() == "") {
                            0
                        } else {
                            i.menuDishPriceInput.editText?.text.toString().toInt()
                        }

                        val dishName = i.menuDishNameInput.editText?.text.toString()
                        val type = pendingList[count].type
                        val option = pendingList[count].option
                        typeNumber = pendingList[count].typeNumber

                        val dish = Dish(type, option, dishName, price, typeNumber)
                        dishList.add(dish)
                        Log.d("menueditfragment", "count=$count dishList = $dishList")
                        count++
                    }
                }


                if (!isNameFilled) {
                    Toast.makeText(this.context, "菜品名稱未完成", Toast.LENGTH_SHORT).show()
                } else if (imgList.isEmpty()) {
                    Toast.makeText(this.context, "請上傳菜單照片", Toast.LENGTH_SHORT).show()
                } else if (dishList.isNullOrEmpty()) {
                    Toast.makeText(this.context, "請新增菜色", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("menuEditfragment", "ok,build!!，，dishList = ${dishList}")
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
                    dishList.clear()
                    findNavController().navigate(MobileNavigationDirections.actionGlobalDoneFragment("chefPage"))
//                typeNumber = -1
                }
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun add(people: Int, percentOff: Int) {
        val discount = Discount(people, percentOff)
        discountList.add(discount)
        Log.d("menueditfragment", "discountList = $discountList")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun remove(position: Int, percentOff: Int) {
        discountList.removeAt(position)
        discountAdapter.submitList(discountList)
        Log.d("menueditfragment", "peopleNumber = $discountList")
        discountAdapter.notifyDataSetChanged()
    }


    private fun addDish(container: ViewGroup?, option: Int,displayTypeList:List<Dish>? ) {

        _dishTypeView =
            ItemAddDishBinding.inflate(LayoutInflater.from(context), container, false)
        bindingList.add(dishTypeView)

        if (option == AddDishType.FIXED.index) {
            dishTypeView.introText.text = "顧客將享用以下所有菜品"
        } else {
            dishTypeView.introText.text = "顧客將在下方選擇一道菜"
        }

        for (t in bindingList) {

            var typeIndex: Int
            //typeSpinner
            val typeList =
                listOf("開胃菜", "湯", "飲料", "酒", "前菜", "主菜", "甜點", "其他")

            val dishTypeAdapter =
                ArrayAdapter(requireContext(), R.layout.list_people_item, typeList)
            (dishTypeView.menuAddDishType.editText as? AutoCompleteTextView)?.setAdapter(
                dishTypeAdapter
            )

//            if(displayTypeList!=null){
//                for (dish in displayTypeList){
//
//
//                }
//            }

            t.removeType.setOnClickListener {
                typeIndex = bindingList.indexOf(t)
                binding.dishTypeLinear.removeView(bindingList[typeIndex].root)
                bindingList.removeAt(typeIndex)
                //*****************************************

                var num = 0
                var count = typeIndex
                for (i in bindingItemCountList) {
                    if (count > 0) {
                        num += i
                        count--
                    }
                }
                val countB = bindingItemCountList[typeIndex]

                removeList(num, countB, pendingList, allDishBindingList)
                bindingItemCountList.removeAt(typeIndex)
            }
            t.addDish.setOnClickListener {
                typeIndex = bindingList.indexOf(t)
                _dishView =
                    ItemDishOptionalBinding.inflate(LayoutInflater.from(context), container, false)

                if (option == AddDishType.FIXED.index) {
                    dishView.menuDishPriceInput.visibility = View.GONE
                }

                val dishBindingList = mutableListOf<ItemDishOptionalBinding>()
                dishBindingList.add(dishView)
                dishView.menuDishNameInput.dispatchSetSelected(true)

                for (d in dishBindingList) {
                    dishType = dishTypeView.menuAddDishType.editText?.text.toString()
                    val pendingDish = Dish(dishType, option, typeNumber = typeIndex)
                    pendingList.add(pendingDish)
                    allDishBindingList.add(d)
                    d.dishRemove.setOnClickListener {

                        val dishIndex = dishBindingList.indexOf(d)
                        typeIndex = bindingList.indexOf(t)

                        bindingList[typeIndex].dishLinear.removeView(dishBindingList[dishIndex].root)
                        dishBindingList.removeAt(dishIndex)
                        //*********************************
                        bindingItemCountList[typeIndex]--
                        Log.d("menueditfragment", "bindingItemCount=$bindingItemCount")

                        //need to remove dish in pending list, done(maybe)
                        allDishBindingList.remove(d)
                        pendingList.remove(pendingDish)
                    }
                }
                bindingList[typeIndex].dishLinear.addView(dishView.root)
                //*********************************
                bindingItemCountList[typeIndex]++
            }
        }
        binding.dishTypeLinear.addView(dishTypeView.root)
        //**********************************
        bindingItemCountList.add(bindingItemCount)
    }

    override fun remove(position: Int) {
        imgList.removeAt(position)
        imageAdapter.submitList(imgList)
        imageAdapter.notifyDataSetChanged()
    }

    private fun removeList(
        num: Int,
        countB: Int,
        pendingList: MutableList<Dish>,
        allDishBindingList: MutableList<ItemDishOptionalBinding>
    ) {
        if (countB < 1) return
        pendingList.removeAt(num)
        allDishBindingList.removeAt(num)
        removeList(num, countB - 1, pendingList, allDishBindingList)
    }

    fun editMenuAddType(container: ViewGroup?, option: Int){
        _dishTypeView =
            ItemAddDishBinding.inflate(LayoutInflater.from(context), container, false)
        bindingList.add(dishTypeView)

        if (option == AddDishType.FIXED.index) {
            dishTypeView.introText.text = "顧客將享用以下所有菜品"
        } else {
            dishTypeView.introText.text = "顧客將在下方選擇一道菜"
        }

    }

    fun editMenuAddDish(){

    }
}



