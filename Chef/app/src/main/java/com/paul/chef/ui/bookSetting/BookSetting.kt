package com.paul.chef.ui.bookSetting

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
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paul.chef.BookSettingType
import com.paul.chef.CalendarType
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.PickerType
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


        binding.bookSettingPickSession.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalPickerBottomSheet(PickerType.SET_SESSION_TIME.index, null) )
        }
        binding.bookSettingSessionCapacity.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalPickerBottomSheet(PickerType.SET_SESSION_CAPACITY.index, null))
        }
        binding.bookSettingStartPick.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalPickerBottomSheet(PickerType.SET_START_TIME.index,null))
        }
        binding.bookSettingEndPick.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalPickerBottomSheet(PickerType.SET_END_TIME.index,null))
        }
        binding.bookSettingCapacity.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalPickerBottomSheet(PickerType.SET_CAPACITY.index,null))
        }

        var capacity = 0
        var sessionCapacity = 0
        var sessionTime = mutableListOf<String>()
        var startTime="null"
        var endTime="null"

        setFragmentResultListener(PickerType.SET_CAPACITY.value) { requestKey, bundle ->
            capacity = bundle.getInt(PickerType.SET_CAPACITY.value)
        }
        setFragmentResultListener(PickerType.SET_SESSION_CAPACITY.value) { requestKey, bundle ->
            sessionCapacity = bundle.getInt(PickerType.SET_SESSION_CAPACITY.value)
        }
        setFragmentResultListener(PickerType.SET_SESSION_TIME.value) { requestKey, bundle ->
            bundle.getString(PickerType.SET_SESSION_TIME.value)?.let { sessionTime.add(it) }
        }
        setFragmentResultListener(PickerType.SET_START_TIME.value) { requestKey, bundle ->
            startTime = bundle.getString(PickerType.SET_START_TIME.value).toString()
        }
        setFragmentResultListener(PickerType.SET_END_TIME.value) { requestKey, bundle ->
            endTime = bundle.getString(PickerType.SET_END_TIME.value).toString()
        }







        binding.save.setOnClickListener {
            val chefSwitch = binding.chefSpaceSwitch.isChecked
            val userSwitch = binding.userSpaceSwitch.isChecked

            if(chefSwitch&&(sessionCapacity==0||sessionTime.size==0)){
                Toast.makeText(this.context, "請設定人數與場次", Toast.LENGTH_SHORT).show()
            }else if(userSwitch&&(capacity==0||startTime=="null"||endTime=="null")){
                Toast.makeText(this.context, "請設定人數與時間", Toast.LENGTH_SHORT).show()
            }else {

                val type: Int = when {
                    chefSwitch && userSwitch -> BookSettingType.AcceptAll.index
                    !chefSwitch && userSwitch -> BookSettingType.OnlyUserSpace.index
                    chefSwitch && !userSwitch -> BookSettingType.OnlyChefSpace.index
                    !chefSwitch && !userSwitch -> BookSettingType.RefuseAll.index
                    else -> {
                        -1
                    }
                }
                //bookSetting
                val chefSpace = ChefSpace(sessionCapacity  , sessionTime)
                val userSpace = UserSpace(capacity, startTime, endTime)

                orderSettingViewModel.setting(type, calendarTypeResult, chefSpace, userSpace)
            }

        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
