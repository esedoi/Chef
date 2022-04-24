package com.paul.chef.ui.transaction

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoOrderDetail
import com.paul.chef.data.Order
import com.paul.chef.databinding.ItemOrderChildBinding
import com.paul.chef.databinding.ItemTransactionUnpaidBinding

class TransactionUnpaidAdapter() : ListAdapter<Order, RecyclerView.ViewHolder>(OrderCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is OrderHolder) {
            holder.bind(item)
        }

    }


    class OrderHolder(private var binding: ItemTransactionUnpaidBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Order) {
            Log.d("transaction", "unpaid_______________")
        }

        companion object {
            fun from(parent: ViewGroup): OrderHolder {
                val order =
                    ItemTransactionUnpaidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return OrderHolder(order)
            }
        }
    }

}

class OrderCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
