package com.paul.chef.ui.addressList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paul.chef.AddressList
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.PickerType
import com.paul.chef.data.Address
import com.paul.chef.databinding.FragmentAddressListBinding
import com.paul.chef.ui.book.BookFragmentArgs


class AddressListFragment : BottomSheetDialogFragment(), AddressList {

    private var _binding: FragmentAddressListBinding? = null
    private val binding get() = _binding!!

    lateinit var addressListAdapter: AddressListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    var addressList = mutableListOf<Address>()

    private val arg: AddressListFragmentArgs by navArgs()

    private lateinit var addressListViewModel: AddressListViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        addressListViewModel =
            ViewModelProvider(this).get(AddressListViewModel::class.java)


        _binding = FragmentAddressListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listType = arg.listType

        binding.addressListCancel.setOnClickListener {
            dismiss()
        }
        binding.addressListDismiss.setOnClickListener{
            dismiss()
        }


        addressListAdapter = AddressListAdapter(this, listType)
        layoutManager = LinearLayoutManager(this.context)
        binding.addressListRecycler.layoutManager = layoutManager
        binding.addressListRecycler.adapter = addressListAdapter
        binding.addressListAddBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalAddAddressFragment())
        }

        addressListViewModel.addressList.observe(viewLifecycleOwner) {
            addressList.clear()
            addressList.addAll(it)
            addressListAdapter.submitList(addressList)
            addressListAdapter.notifyDataSetChanged()
        }
        addressListViewModel.lastSelection.observe(viewLifecycleOwner) {
            addressListAdapter.notifyDataSetChanged()
        }

        setFragmentResultListener("addAddress") { requestKey, bundle ->

            val newAddress = bundle.getParcelable<Address>("address")!!
            Log.d("addresslistfragment", "addAddress=$newAddress")
            addressList.add(newAddress)
            addressListViewModel.updateAddress(addressList)
        }


        return root
    }


    override fun delete(item: Address) {
        addressListViewModel.deleteAddress(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun select(item: Address) {
        addressListAdapter.notifyDataSetChanged()
        setFragmentResult("selectAddress", bundleOf("address" to item))
        dismiss()

    }
}
