package com.paul.chef.ui.menuEdit


import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.AddDiscount
import com.paul.chef.data.Discount
import com.paul.chef.data.Dish
import com.paul.chef.databinding.FragmentMenuEditBinding
import com.paul.chef.databinding.ItemAddDishBinding
import com.paul.chef.databinding.ItemDishOptionalBinding


class MenuEditFragment : Fragment(), AddDiscount {

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

    var discountList = mutableListOf<Discount>()
    var people: Int = 0

    var dishType: String = ""

    var dishList = mutableListOf<Dish>()

    private val arg: MenuEditFragmentArgs by navArgs()


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




        //discount spinner
        val discountSpinner =
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val myAdapter =
            this.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    discountSpinner
                )
            }
        myAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerPeople.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                people = p2 + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.spinnerPeople.adapter = myAdapter

        binding.addDiscountBtn.setOnClickListener {
            if (binding.percentOffInput.text.toString() != "") {
                val percentOff = binding.percentOffInput.text.toString().toInt()
                val discount = Discount(people, percentOff)
                discountList.add(discount)
                discountAdapter.submitList(discountList)
                discountAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this.context, "請填寫折扣", Toast.LENGTH_SHORT).show()
            }

        }


        val addedDish = arg.dish

        Log.d("menuEditfragment", "addedDish = $addedDish")


        binding.fixedBtn.setOnClickListener {
            addDish(container, 0)
        }

        binding.optionalBtn.setOnClickListener {
            addDish(container, 1)
        }


        //create Menu
        binding.build.setOnClickListener {
            if (binding.menuName.text != null || binding.menuIntro.text != null || binding.menuPerPrice.text != null) {
                menuName = binding.menuName.text.toString()
                menuIntro = binding.menuIntro.text.toString()
                perPrice = binding.menuPerPrice.text.toString().toInt()

                var dishName = ""
                var price = -1
                var type = ""
                var option = -1
                var count = 0
                var isNameFilled = true

                for (i in allDishBindingList) {

                    if (i.dishNameInput.text == null) {
                        isNameFilled = false
                    } else {
                        price = if (i.dishPriceInput.text.toString() == "") {
                            0
                        } else {
                            i.dishPriceInput.text.toString().toInt()
                        }

                        dishName = i.dishNameInput.text.toString()
                        type = pendingList[count].type
                        option = pendingList[count].option
                        typeNumber = pendingList[count].typeNumber

                        val dish = Dish(type, option, dishName, price, typeNumber)
                        dishList.add(dish)
                        count++
                    }
                }

                if (isNameFilled) {
                    val images = listOf<String>("photo1", "photo2", "photo3")
                    Log.d("menuEditfragment", "dishList = ${dishList}")
                    menuEditViewModel.createMenu(
                        menuName,
                        menuIntro,
                        perPrice,
                        images,
                        discountList,
                        dishList
                    )
                    dishList.clear()
//                typeNumber = -1
                } else {
                    Toast.makeText(this.context, "菜品名稱未完成", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this.context, "欄位未完成", Toast.LENGTH_SHORT).show()
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

    private fun addDish(container: ViewGroup?, option: Int) {
        _dishTypeView =
            ItemAddDishBinding.inflate(LayoutInflater.from(context), container, false)
        bindingList.add(dishTypeView)
//        typeNumber++

        if (option == 0) {
            dishTypeView.introText.text = "顧客將享用以下所有菜品"
        } else {
            dishTypeView.introText.text = "顧客將在下方選擇一道菜"
        }

        for (t in bindingList) {
            val typeIndex = bindingList.indexOf(t)

            //typeSpinner
            val typeList =
                arrayOf("開胃菜", "湯", "飲料", "酒", "沙拉", "前菜", "主菜", "甜點", "其他")
            val typeAdapter =
                this.context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, typeList) }
            typeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            dishTypeView.dishTypeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        dishType = typeList[p2]
                        Log.d("menueditfragment", "typeList[p2]=${typeList[p2]}")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            dishTypeView.dishTypeSpinner.adapter = typeAdapter

            t.removeType.setOnClickListener {
                binding.dishTypeLinear.removeView(bindingList[typeIndex].root)
                bindingList.removeAt(typeIndex)
            }

            t.addDish.setOnClickListener {
                _dishView =
                    ItemDishOptionalBinding.inflate(LayoutInflater.from(context), container, false)

                if (option == 0) {
                    dishView.dishPriceInput.visibility = View.GONE
                }
                val dishBindingList = mutableListOf<ItemDishOptionalBinding>()
                dishBindingList.add(dishView)

                for (d in dishBindingList) {
                    val pendingDish = Dish(dishType, option, typeNumber = typeIndex)
                    pendingList.add(pendingDish)
                    allDishBindingList.add(d)
                    d.dishRemove.setOnClickListener {
                        val dishIndex = dishBindingList.indexOf(d)
                        bindingList[typeIndex].dishLinear.removeView(dishBindingList[dishIndex].root)
                    }
                }
                bindingList[typeIndex].dishLinear.addView(dishView.root)
            }
        }
        binding.dishTypeLinear.addView(dishTypeView.root)
    }
}
