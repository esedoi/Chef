package com.paul.chef.ui.addressList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paul.chef.AddressList
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.Address
import com.paul.chef.databinding.FragmentAddressListBinding
import com.paul.chef.ext.getVmFactory


class AddressListFragment : BottomSheetDialogFragment(), AddressList {

    private var _binding: FragmentAddressListBinding? = null
    private val binding get() = _binding!!

    private lateinit var addressListAdapter: AddressListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    var addressList = mutableListOf<Address>()

    private val arg: AddressListFragmentArgs by navArgs()

    private val addressListViewModel by viewModels<AddressListViewModel> { getVmFactory() }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAddressListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listType = arg.listType

        binding.addressListCancel.setOnClickListener {
            dismiss()
        }
        binding.addressListDismiss.setOnClickListener {
            dismiss()
        }

        addressListViewModel.liveUser.observe(viewLifecycleOwner) {
            addressListViewModel.getAddress(it)
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
            changeTextVisibility(it)
            addressListAdapter.submitList(addressList)
            addressListAdapter.notifyDataSetChanged()
        }
        addressListViewModel.lastSelection.observe(viewLifecycleOwner) {
            addressListAdapter.notifyDataSetChanged()
        }

        setFragmentResultListener("addAddress") { _, bundle ->
            val newAddress = bundle.getParcelable<Address>("address")!!
            addressList.add(newAddress)
            addressListViewModel.updateAddress(addressList)
        }

        return root
    }

    private fun changeTextVisibility(addressList: List<Address>) {
        if (addressList.isEmpty()) {
            binding.addressListEmptyTxt.visibility = View.VISIBLE
        } else {
            binding.addressListEmptyTxt.visibility = View.GONE
        }
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
