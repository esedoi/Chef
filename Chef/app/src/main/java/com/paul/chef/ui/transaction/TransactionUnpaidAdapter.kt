package com.paul.chef.ui.transaction


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.R
import com.paul.chef.data.Order
import com.paul.chef.databinding.ItemTransactionUnpaidBinding
import java.time.LocalDate



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

            binding.unpaidChefReceive.text = item.chefReceive.toString()
            val str = String.format("%,d", item.chefReceive)
            binding.unpaidChefReceive.text = binding.root.context.getString(R.string.new_taiwan_dollar, str)

            binding.unpaidDate.text = LocalDate.ofEpochDay( item.date).toString()
            binding.unpaidName.text = item.userName
            binding.unpaidPeople.text =  binding.root.context.getString(R.string.people, item.people)


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
