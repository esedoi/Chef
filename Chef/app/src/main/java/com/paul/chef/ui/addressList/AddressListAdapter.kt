package com.paul.chef.ui.addressList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.AddressList
import com.paul.chef.AddressListType
import com.paul.chef.UserManger
import com.paul.chef.data.Address
import com.paul.chef.databinding.ItemAddressListBinding
import com.paul.chef.databinding.ItemAddressListDeleteBinding

class AddressListAdapter(private val addressList: AddressList, private val listType: Int) :
    ListAdapter<Address, RecyclerView.ViewHolder>(
        AddressListCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (listType) {
            AddressListType.NORMAL.index -> {
                AddressHolder.from(parent)
            }
            else -> {
                AddressSelectHolder.from(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is AddressSelectHolder -> {
                holder.bind(item, position, addressList)
            }
            is AddressHolder -> {
                holder.bind(item, position, addressList)
            }
        }
    }

    class AddressSelectHolder(private var binding: ItemAddressListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Address, position: Int, addressList: AddressList) {
            binding.radioButton.text = item.addressTxt
            binding.radioButton.isChecked = UserManger.lastSelectedPosition == position
            binding.radioButton.setOnClickListener {
                addressList.select(item)
                UserManger.lastSelectedPosition = position
            }
        }

        companion object {
            fun from(parent: ViewGroup): AddressSelectHolder {
                val menu =
                    ItemAddressListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AddressSelectHolder(menu)
            }
        }
    }

    class AddressHolder(private var binding: ItemAddressListDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Address, position: Int, addressList: AddressList) {
            binding.itemAddressDTxt.text = item.addressTxt
            binding.itemAddressDDeleBtn.setOnClickListener {
                addressList.delete(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AddressHolder {
                val menu =
                    ItemAddressListDeleteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return AddressHolder(menu)
            }
        }
    }
}

class AddressListCallback : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
