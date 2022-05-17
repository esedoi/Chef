package com.paul.chef.ui.addAddress

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.paul.chef.MainActivity
import com.paul.chef.R
import com.paul.chef.data.Address
import com.paul.chef.databinding.FragmentAddAddressBinding


class AddAddressFragment : DialogFragment(), OnMapReadyCallback {

    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    lateinit var address:Address

    private lateinit var placesClient: PlacesClient



    var mark:LatLng = LatLng(-34.0, 151.0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addAddressCancel.setOnClickListener {
            dismiss()
        }


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
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        placesClient = Places.createClient(requireActivity())



        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS,
            Place.Field.ADDRESS_COMPONENTS
        ))



        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                mMap.clear()
                // TODO: Get info about the selected place.
                Log.d("TAG", "Place: ${place.name}, ${place.addressComponents}, ${place.latLng}")

                mark = place.latLng!!
                mMap.addMarker(MarkerOptions()
                    .position(mark)
                    .title("Marker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark, 16f))
                binding.addAddressTxt.text = place.address
                val newAddress = place.address?:""
                address = Address(newAddress,mark.latitude, mark.longitude )
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })

        binding.addAdressSave.setOnClickListener {
            setFragmentResult("addAddress", bundleOf("address" to address))
            dismiss()
        }


        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val default = LatLng(25.0, 121.0)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(default))

    }


}