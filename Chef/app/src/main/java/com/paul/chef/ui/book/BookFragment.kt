package com.paul.chef.ui.book


import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
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
import com.paul.chef.databinding.FragmentBookBinding
import java.time.LocalDate


class BookFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    var selectDate: Long? = null
    private lateinit var mMap: GoogleMap
    var picker: LocalDate? = null
    private lateinit var placesClient: PlacesClient
//    var bookSetting: BookSetting? = null
    private var chefAddress: Address?=null
    private var userAddress: Address?=null

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

        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )
        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()


        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), key);
        }


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.book_map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        placesClient = Places.createClient(requireActivity())


        //navigation safe args
        val menu = arg.chefMenu
        val selectedDish = arg.selectedDish.toList()
        bookViewModel.getAddress(menu.chefId)
        bookViewModel.chefSpaceAddress.observe(viewLifecycleOwner){
            chefAddress = it
        }



        bookViewModel.priceResult.observe(viewLifecycleOwner) {
            Log.d("bookfragment", "isdiscount=${it["isDiscount"]}")
            if (it["isDiscount"] == 1) {
                binding.apply {
                    orginalPerPrice.visibility = View.VISIBLE
                    orginalTotal.visibility = View.VISIBLE

                    orginalPerPrice.text = getString(R.string.new_taiwan_dollar, String.format("%,d", menu.perPrice))
                    orginalTotal.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["originalPrice"]))
                    orginalPerPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    orginalTotal.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    finalPerPrice.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["discountPerPrice"]))
                    finalTotal.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["total"]))
                    userFee.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["userFee"]))
                    userPay.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["userPay"]))
                }
            } else {
                binding.apply {
                    orginalPerPrice.visibility = View.GONE
                    orginalTotal.visibility = View.GONE
                    finalPerPrice.text = getString(R.string.new_taiwan_dollar, String.format("%,d", menu.perPrice))
                    finalTotal.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["total"]))
                    userFee.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["userFee"]))
                    userPay.text = getString(R.string.new_taiwan_dollar, String.format("%,d", it["userPay"]))
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

            when(checkedId){
                R.id.book_user_space_chip->{
                    findNavController().navigate(MobileNavigationDirections.actionGlobalAddressListFragment(AddressListType.SELECT.index))
                    binding.bookEditAddress.visibility = View.VISIBLE
                }
                R.id.book_chef_space_chip->{
                    binding.bookEditAddress.visibility = View.GONE
                    mMap.clear()
                    if(chefAddress!=null){
                        val latLng = LatLng(chefAddress?.latitude!!,chefAddress?.longitude!!)

                        mMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title("Marker in chefSpace"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                        binding.bookAddress.text = chefAddress?.addressTxt
                    }
                }
            }
        }

        binding.bookEditAddress.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalAddressListFragment(AddressListType.SELECT.index))
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
        setFragmentResultListener(PickerType.PICK_SESSION_CAPACITY.value) { requestKey, bundle ->
            pickPeople = bundle.getInt(PickerType.PICK_SESSION_CAPACITY.value)
            bookViewModel.orderPrice(menu, pickPeople)
            binding.bookPeopleSelect.setText(pickPeople.toString())
        }
        setFragmentResultListener("selectAddress") { requestKey, bundle ->
           val  newAddress = bundle.getParcelable<Address>("address")!!
            val addressTxt = newAddress.addressTxt
            val latLng = LatLng(newAddress.latitude,newAddress.longitude,)
            userAddress = newAddress

            mMap.clear()
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Marker in userSpace"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            binding.bookAddress.text = addressTxt
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

                val address: Address? = if (typeInt == BookType.ChefSpace.index) {
                    chefAddress
                } else {
                    userAddress
                }


                val note = binding.bookNoteLayout.editText?.text.toString()

                if (address != null) {
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
                }else{
                    Toast.makeText(this.context, "請選擇地址", Toast.LENGTH_SHORT).show()
                }
            }
        }

        bookViewModel.bookDone.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
        }

        return root
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

