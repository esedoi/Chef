package com.paul.chef.ui.addressList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.AddressList
import com.paul.chef.UserManger
import com.paul.chef.data.Address
import com.paul.chef.databinding.ItemAddressListBinding


class AddressListAdapter(val addressList: AddressList) : ListAdapter<Address, RecyclerView.ViewHolder>(FriendListCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AddressHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)


        if (holder is AddressHolder) {
            holder.bind(item, position, addressList)
        }

    }



    class AddressHolder(private var binding: ItemAddressListBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(item: Address, position: Int, addressList: AddressList) {

            binding.radioButton.text = item.addressTxt
            binding.radioButton.isChecked =  UserManger.lastSelectedPosition == position


            binding.radioButton.setOnClickListener{
                addressList.select(item)
                UserManger.lastSelectedPosition = position
            }
        }

        companion object {
            fun from(parent: ViewGroup): AddressHolder {
                val menu =
                    ItemAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return AddressHolder(menu)
            }
        }
    }


}

class FriendListCallback : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}
