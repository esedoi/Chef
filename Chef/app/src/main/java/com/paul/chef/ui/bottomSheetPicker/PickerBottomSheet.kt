package com.paul.chef.ui.bottomSheetPicker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paul.chef.PickerType
import com.paul.chef.R
import com.paul.chef.databinding.BottomSheetPickerBinding


class PickerBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetPickerBinding? = null
    private val binding get() = _binding!!

    //safe args
    private val arg: PickerBottomSheetArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetPickerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pickerViewModel =
            ViewModelProvider(this)[PickerViewModel::class.java]

        val pickerType = arg.pickerType
        val chefId = arg.chefId

        if (chefId != null) {
            pickerViewModel.getBookSetting(chefId)
        }
        var time: String
        val zero = "00"
        val thirty = "30"
        var min = true
        val timeList = mutableListOf<String>()
        var pickSessionList = emptyList<String>()
        var pickTimeList = emptyList<String>()

        for (h in 0..23) {
            for (m in 1..2) {
                time = if (min) {
                    "$h:$zero"
                } else {
                    "$h:$thirty"
                }
                timeList.add(time)
                min = !min
            }
        }

        pickerViewModel.bookSetting.observe(viewLifecycleOwner) {
            when (pickerType) {

                PickerType.PICK_SESSION_CAPACITY.index -> {
                    binding.numberPicker.minValue = 1
                    binding.numberPicker.maxValue = it.chefSpace?.sessionCapacity ?: 0
                    binding.pickerTitle.text = getString(R.string.people_number)
                }
                PickerType.PICK_CAPACITY.index -> {
                    binding.numberPicker.minValue = 1
                    binding.numberPicker.maxValue = it.userSpace?.capacity ?: 0
                    binding.pickerTitle.text = getString(R.string.people_number)
                }

                PickerType.PICK_SESSION_TIME.index -> {
                    pickSessionList = it.chefSpace?.session!!
                    binding.numberPicker.minValue = 0
                    binding.numberPicker.maxValue = pickSessionList.size - 1
                    binding.pickerTitle.text = getString(R.string.time)
                    val arrayList = pickSessionList.toTypedArray()
                    binding.numberPicker.displayedValues = arrayList
                }
                PickerType.PICK_TIME.index -> {
                    val startTimeIndex = timeList.indexOf(it.userSpace?.starTime)
                    val endTimeIndex = timeList.indexOf(it.userSpace?.endTime)
                    binding.pickerTitle.text = getString(R.string.time)
                    pickTimeList = timeList.filterIndexed { index, s ->
                        index in startTimeIndex..endTimeIndex
                    }
                    binding.numberPicker.minValue = 0
                    binding.numberPicker.maxValue = pickTimeList.size - 1
                    val arrayList = pickTimeList.toTypedArray()
                    binding.numberPicker.displayedValues = arrayList
                }

            }

        }

        when (pickerType) {

            PickerType.SET_SESSION_TIME.index, PickerType.SET_START_TIME.index, PickerType.SET_END_TIME.index -> {
                binding.numberPicker.minValue = 0
                binding.numberPicker.maxValue = timeList.size - 1
                val arrayList = timeList.toTypedArray()
                binding.numberPicker.displayedValues = arrayList
                binding.pickerTitle.text = getString(R.string.time)
            }

            PickerType.SET_CAPACITY.index, PickerType.SET_SESSION_CAPACITY.index, PickerType.FILTER_PEOPLE.index -> {
                binding.numberPicker.minValue = 1
                binding.numberPicker.maxValue = 30
                binding.pickerTitle.text = getString(R.string.people_number)
            }

        }
        binding.pickerCancel.setOnClickListener {
            dismiss()
        }
        binding.pickerDismiss.setOnClickListener {
            dismiss()
        }

        binding.pickerCheck.setOnClickListener {
            val value = binding.numberPicker.value
            val time: String
            val key: String

            when (pickerType) {
                PickerType.FILTER_PEOPLE.index -> {
                    key = PickerType.FILTER_PEOPLE.value
                    setFragmentResult(key, bundleOf(key to value))
                }
                PickerType.SET_SESSION_TIME.index -> {
                    key = PickerType.SET_SESSION_TIME.value
                    time = timeList[value]
                    setFragmentResult(key, bundleOf(key to time))
                }
                PickerType.SET_START_TIME.index -> {
                    key = PickerType.SET_START_TIME.value
                    time = timeList[value]
                    setFragmentResult(key, bundleOf(key to time))

                }
                PickerType.SET_END_TIME.index -> {
                    key = PickerType.SET_END_TIME.value
                    time = timeList[value]
                    setFragmentResult(key, bundleOf(key to time))

                }
                PickerType.SET_CAPACITY.index -> {
                    key = PickerType.SET_CAPACITY.value
                    setFragmentResult(key, bundleOf(key to value))
                }
                PickerType.SET_SESSION_CAPACITY.index -> {
                    key = PickerType.SET_SESSION_CAPACITY.value
                    setFragmentResult(key, bundleOf(key to value))
                }

                PickerType.PICK_TIME.index -> {
                    key = PickerType.PICK_TIME.value
                    time = pickTimeList[value]
                    setFragmentResult(key, bundleOf(key to time))

                }
                PickerType.PICK_SESSION_TIME.index -> {
                    key = PickerType.PICK_SESSION_TIME.value
                    time = pickSessionList[value]
                    setFragmentResult(key, bundleOf(key to time))

                }
                PickerType.PICK_CAPACITY.index -> {
                    key = PickerType.PICK_CAPACITY.value
                    setFragmentResult(key, bundleOf(key to value))
                }
                PickerType.PICK_SESSION_CAPACITY.index -> {
                    key = PickerType.PICK_SESSION_CAPACITY.value
                    setFragmentResult(key, bundleOf(key to value))
                }
            }
            dismiss()
        }

        return root
    }

}