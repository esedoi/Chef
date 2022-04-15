package com.paul.chef.ui.dishAdd

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.Dish
import com.paul.chef.databinding.BottomSheetAddDishBinding
import com.paul.chef.ui.menuEdit.MenuEditViewModel

class AddDishBottomSheet:BottomSheetDialogFragment() {
    private var _binding: BottomSheetAddDishBinding? = null
    private val binding get() = _binding!!

    var _liveDish = MutableLiveData<Dish>()
    val liveDish: LiveData<Dish>
        get() = _liveDish

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetAddDishBinding.inflate(inflater, container, false)

        val addDishViewModel =
            ViewModelProvider(this).get(AddDishViewModel::class.java)


        //typeSpinner
        val typeList =
            arrayOf("開胃菜", "湯","麵包","飲料", "酒", "沙拉", "副餐", "前菜", "主菜", "甜點", "其他")
        val typeAdapter =
            this.context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, typeList) }
        typeAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.typeSpinner.adapter = typeAdapter

        //optionSpinner
        val optionList =
            arrayOf("固定菜色", "可以替換","加價選擇",)
        val optionAdapter =
            this.context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, typeList) }
        optionAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.optionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.optionSpinner.adapter = optionAdapter



        binding.addDishBtn.setOnClickListener {

            val dishName = binding.dishNameEditText.text.toString()
            val extraPrice = binding.extraPriceEditText.text.toString().toInt()
            val dish = Dish("甜點",3, dishName, extraPrice )
            _liveDish.value = dish

            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuEditFragment(dish))
        }







        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}