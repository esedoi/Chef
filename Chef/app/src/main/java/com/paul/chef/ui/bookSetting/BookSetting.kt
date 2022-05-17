package com.paul.chef.ui.bookSetting


import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
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
import com.google.android.material.chip.Chip
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


        var calendarTypeResult = -1
        val calendarTypeList =
            arrayOf("未來所有日期", "預設所有日期為不可訂")

        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, calendarTypeList)
        (binding.bookSetCalenderDefault.editText as? AutoCompleteTextView)?.setAdapter(adapter)



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



        binding.bookSetChefSpaceTime.setOnClickListener {
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
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalAddressListFragment(
                    AddressListType.SELECT.index
                )
            )
        }


        var capacity = 0
        var sessionCapacity = 0
        val sessionTime = mutableListOf<String>()
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

            val session = bundle.getString(PickerType.SET_SESSION_TIME.value)
            if (session != null) {
                sessionTime.add(session)
            }

            val chip = Chip(this.context)
            chip.text = session
            chip.isCheckable = true
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener { view ->
                binding.bookSetChefSpaceTimeLayout.removeView(view)
                Log.d("booksetting fragment", "chip.text=${chip.text}")
                sessionTime.remove(chip.text)
            }
            binding.bookSetChefSpaceTimeLayout.addView(chip)


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

        orderSettingViewModel.bookSetting.observe(viewLifecycleOwner) {
            calendarTypeResult = it.calendarDefault
            if (it.userSpace != null) {
                capacity = it.userSpace.capacity
                startTime = it.userSpace.starTime
                endTime = it.userSpace.endTime
                binding.bookSetUserSpaceCapacity.setText(it.userSpace.capacity.toString())
                binding.bookSetUserSpaceStartTime.setText(it.userSpace.starTime)
                binding.bookSetUserSpaceEndTime.setText(it.userSpace.endTime)
            }

            if (it.chefSpace != null) {
                sessionCapacity = it.chefSpace.sessionCapacity
                sessionTime.clear()
                sessionTime.addAll(it.chefSpace.session)
                address = it.chefSpace.address
                binding.bookSetChefSpaceAddress.setText(it.chefSpace.address.addressTxt.toString())
                binding.bookSetChefSpaceCapacity.setText(it.chefSpace.sessionCapacity.toString())
            }

            if (it != null) {
                binding.bookSetCalenderDefaultEditTxt.setText(
                    if (it.calendarDefault == CalendarType.AllDayClose.index) {
                        "預設所有日期為不可訂"
                    } else {
                        "未來所有日期"
                    }
                )
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.list_people_item, calendarTypeList)
                (binding.bookSetCalenderDefault.editText as? AutoCompleteTextView)?.setAdapter(
                    adapter
                )


                when (it.type) {
                    BookSettingType.AcceptAll.index -> {
                        binding.chefSpaceCardView.isChecked = true
                        binding.userSpaceCardView.isChecked = true
                        binding.chefSpaceHelper.visibility = View.GONE
                        binding.userSpaceHelper.visibility = View.GONE
                    }
                    BookSettingType.OnlyUserSpace.index -> {
                        binding.chefSpaceCardView.isChecked = false
                        binding.userSpaceCardView.isChecked = true
                        binding.chefSpaceHelper.visibility = View.VISIBLE
                        binding.userSpaceHelper.visibility = View.GONE
                    }
                    BookSettingType.OnlyChefSpace.index -> {
                        binding.chefSpaceCardView.isChecked = true
                        binding.userSpaceCardView.isChecked = false
                        binding.chefSpaceHelper.visibility = View.GONE
                        binding.userSpaceHelper.visibility = View.VISIBLE
                    }
                    BookSettingType.RefuseAll.index -> {
                        binding.chefSpaceCardView.isChecked = false
                        binding.userSpaceCardView.isChecked = false
                        binding.chefSpaceHelper.visibility = View.VISIBLE
                        binding.userSpaceHelper.visibility = View.VISIBLE
                    }
                }
            }
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

                calendarTypeResult = if (calendarTxt == "未來所有日期") {
                    CalendarType.AllDayOpen.index
                } else {
                    CalendarType.AllDayClose.index
                }

                when {
                    chefSpaceCheck && userSpaceCheck -> {
                        val type = BookSettingType.AcceptAll.index
                        val chefSpace = ChefSpace(sessionCapacity, sessionTime, address!!)
                        val userSpace = UserSpace(capacity, startTime, endTime)
                        orderSettingViewModel.setting(
                            type,
                            calendarTypeResult,
                            chefSpace,
                            userSpace
                        )
                        findNavController().navigateUp()
                    }
                    !chefSpaceCheck && userSpaceCheck -> {
                        val type = BookSettingType.OnlyUserSpace.index
                        val userSpace = UserSpace(capacity, startTime, endTime)
                        orderSettingViewModel.setting(
                            type,
                            calendarTypeResult,
                            chefSpace = null,
                            userSpace
                        )
                        findNavController().navigateUp()
                    }
                    chefSpaceCheck && !userSpaceCheck -> {
                        val type = BookSettingType.OnlyChefSpace.index
                        val chefSpace = ChefSpace(sessionCapacity, sessionTime, address!!)
                        orderSettingViewModel.setting(
                            type,
                            calendarTypeResult,
                            chefSpace,
                            userSpace = null
                        )
                        findNavController().navigateUp()
                    }
                    !chefSpaceCheck && !userSpaceCheck -> {
                        val type = BookSettingType.RefuseAll.index
                        orderSettingViewModel.setting(
                            type,
                            calendarTypeResult,
                            chefSpace = null,
                            userSpace = null
                        )
                        findNavController().navigateUp()
                    }
                }
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
