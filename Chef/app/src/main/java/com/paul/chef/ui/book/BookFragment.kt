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




        binding.bookDateSelect.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalDatePicker3(menu.chefId)
            )
        }

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getLong("bundleKey")
            selectDate = result
            Log.d("bookfragment", "result=$result")
            val localDate: LocalDate = LocalDate.ofEpochDay(selectDate!!)
            binding.bookDateSelect.setText(localDate.toString())
        }


        var typeId: Int





        binding.bookPeopleSelect.setOnClickListener {

            typeId = binding.bookChipGroup.checkedChipId

            if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else {

                val typeInt = if (typeId == R.id.book_user_space_chip) {
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

        binding.bookTimeSelect.setOnClickListener {
            typeId = binding.bookChipGroup.checkedChipId

            if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else {
                val typeInt = if (typeId == R.id.book_user_space_chip) {
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


        binding.bookChipGroup.setOnCheckedChangeListener { group, checkedId ->
            typeId = group.checkedChipId
            pickPeople = -1
            pickTime = ""
            binding.bookTimeSelect.text?.clear()
            binding.bookPeopleSelect.text?.clear()
            binding.bookDateSelect.text?.clear()
        }


        setFragmentResultListener(PickerType.PICK_TIME.value) { requestKey, bundle ->
            pickTime = bundle.getString(PickerType.PICK_TIME.value).toString()
            binding.bookTimeSelect.setText(pickTime)
        }
        setFragmentResultListener(PickerType.PICK_SESSION_TIME.value) { requestKey, bundle ->
            pickTime = bundle.getString(PickerType.PICK_SESSION_TIME.value).toString()
            binding.bookTimeSelect.setText(pickTime)
        }
        setFragmentResultListener(PickerType.PICK_CAPACITY.value) { requestKey, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }
        setFragmentResultListener(PickerType.PICK_SESSION_CAPACITY.value) { requestKey, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_SESSION_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }





        binding.pay.setOnClickListener {
            typeId = binding.bookChipGroup.checkedChipId

            if (selectDate == null) {
                Toast.makeText(this.context, "請選擇日期", Toast.LENGTH_SHORT).show()
            } else if (typeId == -1) {
                Toast.makeText(this.context, "請選擇用餐空間", Toast.LENGTH_SHORT).show()
            } else if(pickPeople == -1){
                Toast.makeText(this.context, "請選擇人數", Toast.LENGTH_SHORT).show()
            }else if(pickTime == ""){
                Toast.makeText(this.context, "請選擇用餐時間", Toast.LENGTH_SHORT).show()
            }else {

                val typeInt = if (typeId == R.id.book_user_space_chip) {
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


                val note = binding.bookNoteLayout.editText?.text.toString()

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

