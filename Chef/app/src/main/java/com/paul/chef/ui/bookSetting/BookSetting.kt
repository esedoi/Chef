package com.paul.chef.ui.bookSetting

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.BookSettingType
import com.paul.chef.CalendarType
import com.paul.chef.data.ChefSpace
import com.paul.chef.data.DateSetting
import com.paul.chef.data.MenuStatus
import com.paul.chef.data.UserSpace
import com.paul.chef.databinding.FragmentBookSettingBinding

class BookSetting : Fragment() {

    private var _binding: FragmentBookSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val orderSettingViewModel =
            ViewModelProvider(this).get(BookSettingViewModel::class.java)

        binding.userSpaceSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if(compoundButton.isChecked ){
                binding.userSpaceCardView.visibility = View.VISIBLE
            }else{
                binding.userSpaceCardView.visibility = View.GONE
            }
        }

        binding.chefSpaceSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if(compoundButton.isChecked ){
                binding.chefSpaceCardView.visibility = View.VISIBLE
            }else{
                binding.chefSpaceCardView.visibility = View.GONE
            }
        }


        //calendar spinner
        val calendarTypeList =
            arrayOf("未來所有日期", "預設所有日期為不可訂")
        var calendarTypeResult = -1
        val myAdapter =
            this.context?.let {
                ArrayAdapter(it, R.layout.simple_spinner_item, calendarTypeList)
            }
        myAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.calendarSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    calendarTypeResult = if (calendarTypeList[p2] == "未來所有日期") {
                        CalendarType.AllDayOpen.index
                    } else {
                        CalendarType.AllDayClose.index
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.calendarSpinner.adapter = myAdapter



        //capacity spinner
        val capacityList =
            arrayOf("1 人", "2 人", "3 人", "4 人", "5 人", "6 人", "7 人", "8 人", "9 人", "10 人", "11 人", "12 人", "13 人", "14 人", "15 人", "16 人")
        var capacityResult = -1
        val capacityAdapter =
            this.context?.let {
                ArrayAdapter(it, R.layout.simple_spinner_item, capacityList)
            }
        myAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.userSpaceCapcity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    capacityResult = p2+1
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.userSpaceCapcity.adapter = capacityAdapter


        //capacity spinner
        var sessionCapacityResult = -1
        binding.sessionCapacity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    sessionCapacityResult = p2+1
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.sessionCapacity.adapter = capacityAdapter







        binding.save.setOnClickListener {
            val chefSwitch = binding.chefSpaceSwitch.isChecked
            val userSwitch = binding.userSpaceSwitch.isChecked

            val type: Int = when {
                chefSwitch && userSwitch -> BookSettingType.AcceptAll.index
                !chefSwitch && userSwitch -> BookSettingType.OnlyUserSpace.index
                chefSwitch && !userSwitch -> BookSettingType.OnlyChefSpace.index
                !chefSwitch && !userSwitch -> BookSettingType.RefuseAll.index
                else -> {
                    -1
                }
            }

            //orderSetting
            val chefSpace = ChefSpace(sessionCapacityResult, listOf("12:00", "18:00"))
            val userSpace = UserSpace(capacityResult, "10:00", "21:00")





            orderSettingViewModel.setting(type, calendarTypeResult, chefSpace, userSpace)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
