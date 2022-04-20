package com.paul.chef.ui.orderManage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoOrderDetail
import com.paul.chef.MenuDetail
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.Order
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.databinding.ItemOrderChildBinding

class OrderChildAdapter(val goOrderDetail: GoOrderDetail) : ListAdapter<Order, RecyclerView.ViewHolder>(OrderCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)


        if (holder is OrderHolder) {


            holder.bind(item,goOrderDetail)
        }

    }


    class OrderHolder(private var binding: ItemOrderChildBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Order, goOrderDetail: GoOrderDetail) {
            binding.root.setOnClickListener{
                goOrderDetail.goDetail(item)
                Log.d("orderchildadapter", "item=$item")
            }
//            itemView.setOnClickListener {
//                goDetail.goDetail(item)
//                Log.d("orderchildadapter", "item=$item")
//            }
            binding.orderUserName.text = item.userName
            binding.orderDate.text = item.time
            binding.orderTime.text = item.date.toString()
            binding.orderPeople.text = item.people.toString()
            binding.orderMenu.text = item.menuName
        }

        companion object {
            fun from(parent: ViewGroup): OrderHolder {
                val order =
                    ItemOrderChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
