package com.paul.chef.ui.bookSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.paul.chef.*
import com.paul.chef.data.Address
import com.paul.chef.data.ChefSpace
import com.paul.chef.data.UserSpace
import com.paul.chef.databinding.FragmentBookSettingBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ext.showToast
import com.paul.chef.util.ConstValue.BUNDLE_KEY_ADDRESS
import com.paul.chef.util.ConstValue.DEFAULT_STRING_VALUE
import com.paul.chef.util.ConstValue.REQUEST_KEY_ADDRESS

class BookSettingFragment : Fragment() {

    private var _binding: FragmentBookSettingBinding? = null
    private val binding get() = _binding!!

    private val bookSettingViewModel by viewModels<BookSettingViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var calendarTypeResult: Int
        val calendarTypeList =
            arrayOf(getString(R.string.close_all_day), getString(R.string.open_all_day))

        val adapter = ArrayAdapter(requireContext(), R.layout.list_people_item, calendarTypeList)
        (binding.bookSetCalenderDefault.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.apply {
            userSpaceCardView.setOnClickListener {
                userSpaceCardView.isChecked = !userSpaceCardView.isChecked
                userHelperTextVisibility(userSpaceCardView.isChecked)
            }
            chefSpaceCardView.setOnClickListener {
                chefSpaceCardView.isChecked = !chefSpaceCardView.isChecked
                chefHelperTextVisibility(chefSpaceCardView.isChecked)
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
        var startTime = DEFAULT_STRING_VALUE
        var endTime = DEFAULT_STRING_VALUE
        var address: Address? = null

        setFragmentResultListener(PickerType.SET_CAPACITY.value) { _, bundle ->
            capacity = bundle.getInt(PickerType.SET_CAPACITY.value)
            binding.bookSetUserSpaceCapacity.setText(capacity.toString())
        }
        setFragmentResultListener(PickerType.SET_SESSION_CAPACITY.value) { _, bundle ->
            sessionCapacity = bundle.getInt(PickerType.SET_SESSION_CAPACITY.value)
            binding.bookSetChefSpaceCapacity.setText(sessionCapacity.toString())
        }
        setFragmentResultListener(PickerType.SET_SESSION_TIME.value) { _, bundle ->

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
                sessionTime.remove(chip.text)
            }
            binding.bookSetChefSpaceTimeLayout.addView(chip)
        }
        setFragmentResultListener(PickerType.SET_START_TIME.value) { _, bundle ->
            startTime = bundle.getString(PickerType.SET_START_TIME.value).toString()
            binding.bookSetUserSpaceStartTime.setText(startTime)
        }
        setFragmentResultListener(PickerType.SET_END_TIME.value) { _, bundle ->
            endTime = bundle.getString(PickerType.SET_END_TIME.value).toString()
            binding.bookSetUserSpaceEndTime.setText(endTime)
        }
        setFragmentResultListener(REQUEST_KEY_ADDRESS) { _, bundle ->
            address = bundle.getParcelable(BUNDLE_KEY_ADDRESS)
            val addressTxt = address?.addressTxt ?: ""
            binding.bookSetChefSpaceAddress.setText(addressTxt)
        }

        bookSettingViewModel.bookSetting.observe(viewLifecycleOwner) {
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
                binding.bookSetChefSpaceAddress.setText(it.chefSpace.address.addressTxt)
                binding.bookSetChefSpaceCapacity.setText(it.chefSpace.sessionCapacity.toString())
            }

            if (it != null) {
                binding.bookSetCalenderDefaultEditTxt.setText(
                    if (it.calendarDefault == CalendarType.AllDayClose.index) {
                        getString(R.string.close_all_day)
                    } else {
                        getString(R.string.open_all_day)
                    }
                )
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.list_people_item, calendarTypeList)
                (binding.bookSetCalenderDefault.editText as? AutoCompleteTextView)?.setAdapter(
                    adapter
                )

                changeTypeStatus(it.type)
            }
        }

        binding.save.setOnClickListener {
            val chefSpaceCheck = binding.chefSpaceCardView.isChecked
            val userSpaceCheck = binding.userSpaceCardView.isChecked
            val calendarTxt = binding.bookSetCalenderDefault.editText?.text.toString()

            if (chefSpaceCheck && (sessionCapacity == 0 || sessionTime.size == 0 || address == null)) {
                activity?.showToast(getString(R.string.please_set_capacity_session))
            } else if (userSpaceCheck && (capacity == 0 || startTime == DEFAULT_STRING_VALUE || endTime == DEFAULT_STRING_VALUE)) {
                activity?.showToast(getString(R.string.please_set_capacity_time))

            } else if (calendarTxt == DEFAULT_STRING_VALUE) {
                activity?.showToast(getString(R.string.please_set_calendar_default))

            } else {
                calendarTypeResult = if (calendarTxt == getString(R.string.open_all_day)) {
                    CalendarType.AllDayOpen.index
                } else {
                    CalendarType.AllDayClose.index
                }

                when {
                    chefSpaceCheck && userSpaceCheck -> {
                        val type = BookSettingType.AcceptAll.index
                        val chefSpace = ChefSpace(sessionCapacity, sessionTime, address!!)
                        val userSpace = UserSpace(capacity, startTime, endTime)
                        bookSettingViewModel.setting(
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
                        bookSettingViewModel.setting(
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
                        bookSettingViewModel.setting(
                            type,
                            calendarTypeResult,
                            chefSpace,
                            userSpace = null
                        )
                        findNavController().navigateUp()
                    }
                    !chefSpaceCheck && !userSpaceCheck -> {
                        val type = BookSettingType.RefuseAll.index
                        bookSettingViewModel.setting(
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

    private fun chefHelperTextVisibility(checked: Boolean) {
        when (checked) {
            true -> {
                binding.chefSpaceHelper.visibility = View.GONE
            }
            false -> {
                binding.chefSpaceHelper.visibility = View.VISIBLE
            }
        }
    }

    private fun userHelperTextVisibility(checked: Boolean) {
        when (checked) {
            true -> {
                binding.userSpaceHelper.visibility = View.GONE
            }
            false -> {
                binding.userSpaceHelper.visibility = View.VISIBLE
            }
        }
    }

    private fun changeTypeStatus(type: Int) {
        when (type) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
