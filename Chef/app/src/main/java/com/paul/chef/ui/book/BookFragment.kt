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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.paul.chef.BookType
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.PickerType
import com.paul.chef.R
import com.paul.chef.data.BookSetting
import com.paul.chef.data.Dish
import com.paul.chef.data.SelectedDate
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
//    var bookSetting: BookSetting? = null

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


        bookViewModel.priceResult.observe(viewLifecycleOwner) {
            Log.d("bookfragment", "isdiscount=${it["isDiscount"]}")
            if (it["isDiscount"] == 1) {
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
            } else {
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




        binding.datepicker.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalDatePicker3(menu.chefId)
            )
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getLong("bundleKey")
            selectDate = result
            Log.d("bookfragment", "result=$result")
        }


        var typeId: Int





        binding.bookPeoplerPicker.setOnClickListener {
            typeId = binding.orderTypeGroup.checkedRadioButtonId

            if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else {

                val typeInt = if (typeId == R.id.userSpace) {
                    BookType.UserSpace.index
                } else {
                    BookType.ChefSpace.index
                }

                val safeArg = if(typeInt ==BookType.UserSpace.index){
                    PickerType.PICK_CAPACITY.index
                }else{
                    PickerType.PICK_SESSION_CAPACITY.index
                }
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalPickerBottomSheet(
                        safeArg,menu.chefId
                    )
                )
            }
        }

        binding.bookTimePicker.setOnClickListener {
            typeId = binding.orderTypeGroup.checkedRadioButtonId

            if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else {
                val typeInt = if (typeId == R.id.userSpace) {
                    BookType.UserSpace.index
                } else {
                    BookType.ChefSpace.index
                }

                val safeArg = if(typeInt ==BookType.UserSpace.index){
                    PickerType.PICK_TIME.index
                }else{
                    PickerType.PICK_SESSION_TIME.index
                }

                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalPickerBottomSheet(
                        safeArg,menu.chefId
                    )
                )
            }
        }



        var pickPeople = -1
        var pickTime = ""

        binding.orderTypeGroup.setOnCheckedChangeListener { radioGroup, i ->
            typeId = binding.orderTypeGroup.checkedRadioButtonId
            pickPeople = -1
            pickTime = ""
        }

        setFragmentResultListener(PickerType.PICK_TIME.value) { requestKey, bundle ->
            pickTime = bundle.getString(PickerType.PICK_TIME.value).toString()
        }
        setFragmentResultListener(PickerType.PICK_SESSION_TIME.value) { requestKey, bundle ->
            pickTime = bundle.getString(PickerType.PICK_TIME.value).toString()
        }
        setFragmentResultListener(PickerType.PICK_CAPACITY.value) { requestKey, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
        }
        setFragmentResultListener(PickerType.PICK_SESSION_CAPACITY.value) { requestKey, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_SESSION_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
        }





        binding.pay.setOnClickListener {
           typeId = binding.orderTypeGroup.checkedRadioButtonId

            if (selectDate == null) {
                Toast.makeText(this.context, "請選擇日期", Toast.LENGTH_SHORT).show()
            } else if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else if(pickPeople == -1){
                Toast.makeText(this.context, "請選擇人數", Toast.LENGTH_SHORT).show()
            }else if(pickTime == ""){
                Toast.makeText(this.context, "請選擇用餐時間", Toast.LENGTH_SHORT).show()
            }else {

                val typeInt = if (typeId == R.id.userSpace) {
                    BookType.UserSpace.index
                } else {
                    BookType.ChefSpace.index
                }
                Log.d("bookfragment", "typeId = $typeId")
                Log.d("bookfragment", "typeInt = $typeInt")

                val address: String = if (typeInt == 1) {
                    "chefAddress"
                } else {
                    "userAddress"
                }


                val note = binding.noteInput.text.toString()

                bookViewModel.book(
                    menu,
                    typeInt,
                    address,
                    selectDate!!,
                    pickTime,
                    note,
                    pickPeople,
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

