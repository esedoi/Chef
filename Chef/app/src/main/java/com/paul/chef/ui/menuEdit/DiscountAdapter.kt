package com.paul.chef.ui.menuEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.AddDiscount
import com.paul.chef.data.Discount
import com.paul.chef.databinding.ItemAddedDiscountBinding

class DiscountAdapter(val addDiscount:AddDiscount) : ListAdapter<Discount, RecyclerView.ViewHolder>(FriendListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticlesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is ArticlesHolder) {
            holder.bind(item, addDiscount, position)
        }

    }


    class ArticlesHolder(private var binding: ItemAddedDiscountBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Discount, addDiscount: AddDiscount, position: Int) {
//            binding.discountDiscription.text = "你的 ${item.people} 人價格，扣除${item.percentOff}% 的折扣後是"
            binding.peopleNumber.text = item.people.toString()
            binding.percentOff.text = item.percentOff.toString()
            binding.remove.setOnClickListener {
                addDiscount.remove(position, item.percentOff)
            }

        }

        companion object {
            fun from(parent: ViewGroup): ArticlesHolder {
                val friend =
                    ItemAddedDiscountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArticlesHolder(friend)
            }
        }
    }

}

class FriendListCallback : DiffUtil.ItemCallback<Discount>() {
    override fun areItemsTheSame(oldItem: Discount, newItem: Discount): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Discount, newItem: Discount): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
