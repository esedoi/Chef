package com.paul.chef.ui.bookSetting


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paul.chef.*
import com.paul.chef.data.Address
import com.paul.chef.data.ChefSpace
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



        binding.apply {
            userSpaceCardView.setOnClickListener {
                userSpaceCardView.isChecked = !userSpaceCardView.isChecked
                when (userSpaceCardView.isChecked) {
                    true -> {
                        userSpaceHelper.visibility = View.GONE
                    }
                    false -> {
                        userSpaceHelper.visibility = View.VISIBLE

                    }
                }

            }
            chefSpaceCardView.setOnClickListener {
                chefSpaceCardView.isChecked = !chefSpaceCardView.isChecked
                when (chefSpaceCardView.isChecked) {
                    true -> {
                        chefSpaceHelper.visibility = View.GONE
                    }
                    false -> {
                        chefSpaceHelper.visibility = View.VISIBLE
                    }
                }
            }
        }


        var calendarTypeResult = -1
        val calendarTypeList =
            arrayOf("未來所有日期", "預設所有日期為不可訂")

        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, calendarTypeList)
        (binding.bookSetCalenderDefault.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        binding.bookSetChefSpaceTime.setOnClickListener {
            Log.d("booksettingfragment", "onclicklistener")
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalPickerBottomSheet(
                    PickerType.SET_SESSION_TIME.index,
                    null
                )
            )
        }

        binding.bookSetChefSpaceCapacity.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalPickerBottomSheet(
                    PickerType.SET_SESSION_CAPACITY.index,
                    null
                )
            )
        }


        binding.bookSetUserSpaceStartTime.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalPickerBottomSheet(
                    PickerType.SET_START_TIME.index,
                    null
                )
            )
        }
        binding.bookSetUserSpaceEndTime.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalPickerBottomSheet(
                    PickerType.SET_END_TIME.index,
                    null
                )
            )
        }
        binding.bookSetUserSpaceCapacity.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalPickerBottomSheet(
                    PickerType.SET_CAPACITY.index,
                    null
                )
            )
        }
        binding.bookSetChefSpaceAddress.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalAddressListFragment())
        }


        var capacity = 0
        var sessionCapacity = 0
        var sessionTime = mutableListOf<String>()
        var startTime = "null"
        var endTime = "null"
        var address: Address? = null

        setFragmentResultListener(PickerType.SET_CAPACITY.value) { requestKey, bundle ->
            capacity = bundle.getInt(PickerType.SET_CAPACITY.value)
            binding.bookSetUserSpaceCapacity.setText(capacity.toString())
        }
        setFragmentResultListener(PickerType.SET_SESSION_CAPACITY.value) { requestKey, bundle ->
            sessionCapacity = bundle.getInt(PickerType.SET_SESSION_CAPACITY.value)
            binding.bookSetChefSpaceCapacity.setText(sessionCapacity.toString())
        }
        setFragmentResultListener(PickerType.SET_SESSION_TIME.value) { requestKey, bundle ->
            bundle.getString(PickerType.SET_SESSION_TIME.value)?.let { sessionTime.add(it) }
        }
        setFragmentResultListener(PickerType.SET_START_TIME.value) { requestKey, bundle ->
            startTime = bundle.getString(PickerType.SET_START_TIME.value).toString()
            binding.bookSetUserSpaceStartTime.setText(startTime)
        }
        setFragmentResultListener(PickerType.SET_END_TIME.value) { requestKey, bundle ->
            endTime = bundle.getString(PickerType.SET_END_TIME.value).toString()
            binding.bookSetUserSpaceEndTime.setText(endTime)
        }
        setFragmentResultListener("selectAddress") { requestKey, bundle ->
            address = bundle.getParcelable<Address>("address")!!
            val addressTxt = address?.addressTxt ?: ""
            binding.bookSetChefSpaceAddress.setText(addressTxt)
        }


        binding.save.setOnClickListener {


            val chefSpaceCheck = binding.chefSpaceCardView.isChecked
            val userSpaceCheck = binding.userSpaceCardView.isChecked
            val calendarTxt = binding.bookSetCalenderDefault.editText?.text.toString()

            if (chefSpaceCheck && (sessionCapacity == 0 || sessionTime.size == 0 || address == null)) {
                Toast.makeText(this.context, "請設定人數與場次", Toast.LENGTH_SHORT).show()
            } else if (userSpaceCheck && (capacity == 0 || startTime == "null" || endTime == "null")) {
                Toast.makeText(this.context, "請設定人數與時間", Toast.LENGTH_SHORT).show()
            } else if (calendarTxt == "") {
                Toast.makeText(this.context, "請選擇可預訂期間", Toast.LENGTH_SHORT).show()
            } else {

                val type: Int = when {
                    chefSpaceCheck && userSpaceCheck -> BookSettingType.AcceptAll.index
                    !chefSpaceCheck && userSpaceCheck -> BookSettingType.OnlyUserSpace.index
                    chefSpaceCheck && !userSpaceCheck -> BookSettingType.OnlyChefSpace.index
                    !chefSpaceCheck && !userSpaceCheck -> BookSettingType.RefuseAll.index
                    else -> {
                        -1
                    }
                }
                //bookSetting
                val chefSpace = ChefSpace(sessionCapacity, sessionTime, address!!)
                val userSpace = UserSpace(capacity, startTime, endTime)



                calendarTypeResult = if (calendarTxt == "未來所有日期") {
                    CalendarType.AllDayOpen.index
                } else {
                    CalendarType.AllDayClose.index
                }

                orderSettingViewModel.setting(type, calendarTypeResult, chefSpace, userSpace)
                findNavController().navigateUp()

            }

        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
