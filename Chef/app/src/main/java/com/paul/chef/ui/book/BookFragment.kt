package com.paul.chef.ui.book

import android.annotation.SuppressLint
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.location.Address
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
import com.google.android.material.datepicker.MaterialDatePicker
import com.paul.chef.R
import com.paul.chef.data.Dish
import com.paul.chef.databinding.FragmentBookBinding
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.ui.chef.ChefViewModel
import com.paul.chef.ui.menuDetail.MenuDetailFragmentArgs
import java.time.LocalDate
import java.util.*

class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    var selectDate: Long? = null

    var picker: LocalDate? = null

    //safe args
    private val arg: BookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookViewModel =
            ViewModelProvider(this).get(BookViewModel::class.java)

        _binding = FragmentBookBinding.inflate(inflater, container, false)
        val root: View = binding.root




        //navigation safe args
        val menu = arg.chefMenu
        val selectedDish = arg.selectedDish.toList()

        bookViewModel.priceResult.observe(viewLifecycleOwner){
            Log.d("bookfragment","isdiscount=${it["isDiscount"]}")
            if(it["isDiscount"] ==1){
                binding.apply {
                    orginalPerPrice.visibility = View.VISIBLE
                    orginalTotal.visibility = View.VISIBLE
                    orginalPerPrice.text = menu.perPrice.toString()
                    orginalTotal.text = it["originalPrice"].toString()
                    orginalPerPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    orginalTotal.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    finalPerPrice.text = it["discountPerPrice"].toString()
                    finalTotal.text = it["total"].toString()
                    userFee.text = it["userFee"].toString()
                    userPay.text = it["userPay"].toString()
                }
                }else{
                binding.apply {
                    orginalPerPrice.visibility = View.GONE
                    orginalTotal.visibility = View.GONE
                    finalPerPrice.text = menu.perPrice.toString()
                    finalTotal.text = it["total"].toString()
                    userFee.text = it["userFee"].toString()
                    userPay.text = it["userPay"].toString()
                }
                }
            }



        var datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select date")
                .build()


        binding.datepicker.setOnClickListener {
            datePicker.show(this.parentFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener {
            Log.d("bookfragment", "datePicker.selection=${datePicker.selection}")
            selectDate = datePicker.selection
        }

        var people = -1
        //peopleSpinner
        val peopleList =
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val peopleAdapter =
            this.context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, peopleList) }
        peopleAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.peopleSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    people = peopleList[p2].toInt()
                    bookViewModel.orderPrice(menu, people)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.peopleSpinner.adapter = peopleAdapter


        binding.pay.setOnClickListener {
            val typeId = binding.orderTypeGroup.checkedRadioButtonId

            if (people == -1) {
                Toast.makeText(this.context, "請選擇菜品", Toast.LENGTH_SHORT).show()
            } else if (selectDate == null) {
                Toast.makeText(this.context, "請選擇時間", Toast.LENGTH_SHORT).show()
            } else if(typeId ==-1){
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            }else {

                val typeInt = if (typeId == R.id.userSpace) {
                    0
                } else {
                    1
                }
                Log.d("bookfragment", "typeId = $typeId")
                Log.d("bookfragment", "typeInt = $typeInt")

                val address: String = if (typeInt == 1) {
                    "chefAddress"
                } else {
                    "userAddress"
                }
                val time = "19:00"

                val note = binding.noteInput.text.toString()

                bookViewModel.book(
                    menu,
                    typeInt,
                    address,
                    selectDate!!,
                    time,
                    note,
                    people,
                    selectedDish
                )

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

