package com.paul.chef.ui.book

import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.paul.chef.*
import com.paul.chef.data.Address
import com.paul.chef.data.Menu
import com.paul.chef.databinding.FragmentBookBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ext.showToast
import com.paul.chef.util.ConstValue.BUNDLE_KEY_ADDRESS
import com.paul.chef.util.ConstValue.BUNDLE_KEY_SELECTED_DATE
import com.paul.chef.util.ConstValue.DEFAULT_INT_VALUE
import com.paul.chef.util.ConstValue.DEFAULT_STRING_VALUE
import com.paul.chef.util.ConstValue.DISCOUNT_PER_PRICE
import com.paul.chef.util.ConstValue.IS_DISCOUNT
import com.paul.chef.util.ConstValue.ORIGINAL_PRICE
import com.paul.chef.util.ConstValue.REQUEST_KEY_ADDRESS
import com.paul.chef.util.ConstValue.REQUEST_KEY_DATE_PICKER
import com.paul.chef.util.ConstValue.TOTAL
import com.paul.chef.util.ConstValue.USER_FEE
import com.paul.chef.util.ConstValue.USER_PAY
import com.paul.chef.util.Util.getPrice
import java.time.LocalDate

class BookFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private var selectDate: Long? = null
    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var chefAddress: Address
    private lateinit var userAddress: Address
    private val arg: BookFragmentArgs by navArgs()
    lateinit var menu: Menu

    private val bookViewModel by viewModels<BookViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // map
        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )
        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), key)
        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.book_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        placesClient = Places.createClient(requireActivity())

        // navigation safe args
        menu = arg.menu

        val selectedDish = arg.selectedDish.toList()
        bookViewModel.getAddress(menu.chefId)
        bookViewModel.chefSpaceAddress.observe(viewLifecycleOwner) {
            chefAddress = it
            when (arg.type) {
                BookSettingType.OnlyChefSpace.index -> {
                    binding.bookChefSpaceChip.isChecked = true
                    binding.bookChefSpaceChip.isEnabled = true
                    binding.bookUserSpaceChip.isChecked = false
                    binding.bookUserSpaceChip.isEnabled = false
                    binding.bookEditAddress.visibility = View.GONE
                    mMap.clear()
                    val latLng = LatLng(chefAddress.latitude, chefAddress.longitude)

                    mMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    binding.bookAddress.text = chefAddress.addressTxt
                }
                BookSettingType.OnlyUserSpace.index -> {
                    binding.bookChefSpaceChip.isChecked = false
                    binding.bookChefSpaceChip.isEnabled = false
                    binding.bookUserSpaceChip.isChecked = true
                    binding.bookUserSpaceChip.isEnabled = true
                }
            }
        }

        // price result
        bookViewModel.priceResult.observe(viewLifecycleOwner) {
            if (it[IS_DISCOUNT] == 1 && it != null) {
                binding.apply {
                    orginalPerPrice.visibility = View.VISIBLE
                    orginalTotal.visibility = View.VISIBLE
                    orginalPerPrice.text = getPrice(menu.perPrice)
                    orginalTotal.text = it[ORIGINAL_PRICE]?.let { it1 -> getPrice(it1) }
                    orginalPerPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    orginalTotal.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    finalPerPrice.text = it[DISCOUNT_PER_PRICE]?.let { it1 -> getPrice(it1) }
                    finalTotal.text = it[TOTAL]?.let { it1 -> getPrice(it1) }
                    userFee.text = it[USER_FEE]?.let { it1 -> getPrice(it1) }
                    userPay.text = it[USER_PAY]?.let { it1 -> getPrice(it1) }

                }
            } else {
                binding.apply {
                    orginalPerPrice.visibility = View.GONE
                    orginalTotal.visibility = View.GONE
                    finalPerPrice.text = getPrice(menu.perPrice)

                    finalTotal.text = it[TOTAL]?.let { it1 -> getPrice(it1) }
                    userFee.text = it[USER_FEE]?.let { it1 -> getPrice(it1) }
                    userPay.text = it[USER_PAY]?.let { it1 -> getPrice(it1) }
                }
            }
        }

        binding.bookDateSelect.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalDatePicker3(menu.chefId)
            )
        }

        setFragmentResultListener(REQUEST_KEY_DATE_PICKER) { _, bundle ->
            val result = bundle.getLong(BUNDLE_KEY_SELECTED_DATE)
            selectDate = result
            val localDate: LocalDate? = selectDate?.let { LocalDate.ofEpochDay(it) }
            binding.bookDateSelect.setText(localDate.toString())
        }

        var typeId: Int

        binding.bookPeopleSelect.setOnClickListener {
            typeId = binding.bookChipGroup.checkedChipId

            if (whetherTypeSelected(typeId)) {
                val typeInt: Int = getTypeInt(typeId)
                val safeArg: Int = getPeopleSafeArg(typeInt)
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalPickerBottomSheet(safeArg, menu.chefId)
                )
            }
        }

        binding.bookTimeSelect.setOnClickListener {
            typeId = binding.bookChipGroup.checkedChipId

            if (whetherTypeSelected(typeId)) {
                val typeInt = getTypeInt(typeId)
                val safeArg: Int = getTimeSafeArg(typeInt)
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalPickerBottomSheet(safeArg, menu.chefId)
                )
            }
        }

        var pickPeople = DEFAULT_INT_VALUE
        var pickTime = DEFAULT_STRING_VALUE

        binding.bookChipGroup.setOnCheckedChangeListener { group, checkedId ->
            typeId = group.checkedChipId
            pickPeople = DEFAULT_INT_VALUE
            pickTime = DEFAULT_STRING_VALUE
            binding.bookTimeSelect.text?.clear()
            binding.bookPeopleSelect.text?.clear()
            binding.bookDateSelect.text?.clear()
            binding.bookAddress.text = "--"

            when (checkedId) {
                R.id.book_user_space_chip -> {
                    findNavController().navigate(
                        MobileNavigationDirections.actionGlobalAddressListFragment(
                            AddressListType.SELECT.index
                        )
                    )
                    binding.bookEditAddress.visibility = View.VISIBLE
                }
                R.id.book_chef_space_chip -> {
                    binding.bookEditAddress.visibility = View.GONE
                    mMap.clear()
                    val latLng = LatLng(chefAddress.latitude, chefAddress.longitude)

                    mMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                    binding.bookAddress.text = chefAddress.addressTxt
                }
            }
        }

        binding.bookEditAddress.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalAddressListFragment(
                    AddressListType.SELECT.index
                )
            )
        }

        setFragmentResultListener(PickerType.PICK_TIME.value) { _, bundle ->
            pickTime = bundle.getString(PickerType.PICK_TIME.value).toString()
            binding.bookTimeSelect.setText(pickTime)
        }
        setFragmentResultListener(PickerType.PICK_SESSION_TIME.value) { _, bundle ->
            pickTime = bundle.getString(PickerType.PICK_SESSION_TIME.value).toString()
            binding.bookTimeSelect.setText(pickTime)
        }
        setFragmentResultListener(PickerType.PICK_CAPACITY.value) { _, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }
        setFragmentResultListener(PickerType.PICK_SESSION_CAPACITY.value) { _, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_SESSION_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }
        setFragmentResultListener(PickerType.PICK_SESSION_CAPACITY.value) { _, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_SESSION_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }
        setFragmentResultListener(REQUEST_KEY_ADDRESS) { _, bundle ->
            val newAddress = bundle.getParcelable<Address>(BUNDLE_KEY_ADDRESS)
            newAddress?.let {
                val addressTxt = newAddress.addressTxt
                val latLng = LatLng(newAddress.latitude, newAddress.longitude)
                userAddress = newAddress

                mMap.clear()
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                binding.bookAddress.text = addressTxt
            }
        }

        binding.pay.setOnClickListener {
            typeId = binding.bookChipGroup.checkedChipId
            val typeInt = getTypeInt(typeId)
            val address: Address? = getAddress(typeInt)

            val errorHandleResult: Boolean =
                checkErrorHandleResult(selectDate, pickPeople, pickTime, address)

            if (errorHandleResult && address != null) {
                val note = binding.bookNoteLayout.editText?.text.toString()
                selectDate?.let {
                    bookViewModel.book(
                        menu,
                        typeInt,
                        address,
                        it,
                        pickTime,
                        note,
                        pickPeople,
                        selectedDish
                    )
                }
            }
        }

        bookViewModel.bookDone.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalDoneFragment("orderMange")
                )
            }
        }

        return root
    }

    private fun getPeopleSafeArg(typeInt: Int): Int {
        return if (typeInt == BookType.UserSpace.index) {
            PickerType.PICK_CAPACITY.index
        } else {
            PickerType.PICK_SESSION_CAPACITY.index
        }
    }

    private fun getTimeSafeArg(typeInt: Int): Int {
        return if (typeInt == BookType.UserSpace.index) {
            PickerType.PICK_TIME.index
        } else {
            PickerType.PICK_SESSION_TIME.index
        }
    }

    private fun whetherTypeSelected(typeId: Int): Boolean {
        return if (typeId == DEFAULT_INT_VALUE) {
            activity?.showToast(getString(R.string.please_select_space))
            false
        } else {
            true
        }
    }


    private fun checkErrorHandleResult(
        selectDate: Long?,
        pickPeople: Int,
        pickTime: String,
        address: Address?,
    ): Boolean {
        return when {
            selectDate == null -> {
                activity?.showToast(getString(R.string.please_select_date))
                false
            }
            pickPeople == DEFAULT_INT_VALUE -> {
                activity?.showToast(getString(R.string.please_select_people))
                false
            }
            pickTime == DEFAULT_STRING_VALUE -> {
                activity?.showToast(getString(R.string.please_select_time))
                false
            }
            address == null -> {
                activity?.showToast(getString(R.string.please_select_address))
                false
            }
            else -> {
                true
            }
        }
    }


    private fun getAddress(typeInt: Int): Address? {
        return try {
            if (typeInt == BookType.ChefSpace.index) {
                chefAddress
            } else {
                userAddress
            }
        } catch (e: Exception) {
            null
        }

    }

    private fun getTypeInt(typeId: Int): Int {
        return if (typeId == R.id.book_user_space_chip) {
            BookType.UserSpace.index
        } else {
            BookType.ChefSpace.index
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val default = LatLng(25.0, 121.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(default))
    }
}
