package com.paul.chef.ui.transaction

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoOrderDetail
import com.paul.chef.data.Order
import com.paul.chef.data.Transaction
import com.paul.chef.databinding.ItemOrderChildBinding
import com.paul.chef.databinding.ItemTransactionChildBinding

class TransactionChildAdapter() : ListAdapter<Transaction, RecyclerView.ViewHolder>(TransactionCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is OrderHolder) {
            holder.bind(item)
        }

    }


    class OrderHolder(private var binding: ItemTransactionChildBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Transaction) {

          Log.d("transaction", "child_____________")
        }

        companion object {
            fun from(parent: ViewGroup): OrderHolder {
                val order =
                    ItemTransactionChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return OrderHolder(order)
            }
        }
    }

}

class TransactionCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}