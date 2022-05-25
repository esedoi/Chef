package com.paul.chef.ui.filterSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.*
import com.paul.chef.data.Address
import com.paul.chef.databinding.FragmentFilterBinding

class FilterFragment : DialogFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.airbnb.lottie.R.style.Base_Theme_AppCompat_Light_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val filterViewModel =
            ViewModelProvider(this).get(FilterViewModel::class.java)
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var space: Int? = null
        var place: Address? = null
        var date: Long? = null
        var people: Int? = null

//        binding.filterCancel.setOnClickListener {
//            findNavController().navigateUp()
//        }
//        binding.filterDismiss.setOnClickListener {
//            findNavController().navigateUp()
//        }

//        binding.filterPeopleSelect.setOnClickListener {
//            findNavController().navigate(
//                MobileNavigationDirections.actionGlobalPickerBottomSheet(
//                    PickerType.FILTER_PEOPLE.index,
//                    null
//                )
//            )
//        }
//        setFragmentResultListener(PickerType.FILTER_PEOPLE.value) { requestKey, bundle ->
//            val result = bundle.getInt(PickerType.FILTER_PEOPLE.value)
//            people = result
//            binding.filterPeopleSelect.setText(result.toString())
//        }

//        binding.filterAddressTxt.setOnClickListener {
//            findNavController().navigate(
//                MobileNavigationDirections.actionGlobalAddressListFragment(
//                    AddressListType.SELECT.index
//                )
//            )
//        }
//        setFragmentResultListener("selectAddress") { requestKey, bundle ->
//            val result = bundle.getParcelable<Address>("address")!!
//            val addressTxt = result.addressTxt
//            val latLng = LatLng(result.latitude, result.longitude)
//            place = result
//            binding.filterAddressTxt.setText(addressTxt)
//        }

//        binding.filterDateSelect.setOnClickListener {
//            findNavController().navigate(
//                MobileNavigationDirections.actionGlobalDatePicker3(null)
//            )
//        }
//        setFragmentResultListener("filterDate") { requestKey, bundle ->
//            val result = bundle.getLong("date")
//            val localDate: LocalDate = LocalDate.ofEpochDay(result)
//            date = result
//            binding.filterDateSelect.setText(localDate.toString())
//        }

        binding.filterCheck.setOnClickListener {

            val userSpaceCheck = binding.filterUserSpaceChip.isChecked
            val chefSpaceCheck = binding.filterChefSpaceChip.isChecked
            when {
                chefSpaceCheck && userSpaceCheck -> {
                    space = BookSettingType.AcceptAll.index
                }
                !chefSpaceCheck && userSpaceCheck -> {
                    space = BookSettingType.OnlyUserSpace.index
                }
                chefSpaceCheck && !userSpaceCheck -> {
                    space = BookSettingType.OnlyChefSpace.index
                }
                !chefSpaceCheck && !userSpaceCheck -> {
                    space = BookSettingType.RefuseAll.index
                }
            }
            setFragmentResult(
                "filter",
                bundleOf("space" to space, "place" to place, "date" to date, "people" to people)
            )

            dismiss()
        }

        return root
    }
}
