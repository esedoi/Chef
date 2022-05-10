package com.paul.chef.ui.transaction


import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.R
import com.paul.chef.data.Transaction
import com.paul.chef.databinding.ItemTransactionChildBinding
import java.time.LocalDate
import java.util.*

class TransactionChildAdapter() :
    ListAdapter<Transaction, RecyclerView.ViewHolder>(TransactionCallback()) {


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
            binding.transacOrder.text =
                binding.root.context.getString(R.string.transaction_number, item.orderList.size)
            val sdf = SimpleDateFormat("yyyy MM-dd")
            binding.transacDate.text = sdf.format(Date(item.time))
            val str = String.format("%,d", item.chefReceive)
            binding.transacChefReceive.text =
                binding.root.context.getString(R.string.new_taiwan_dollar, str)
        }

        companion object {
            fun from(parent: ViewGroup): OrderHolder {
                val order =
                    ItemTransactionChildBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
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