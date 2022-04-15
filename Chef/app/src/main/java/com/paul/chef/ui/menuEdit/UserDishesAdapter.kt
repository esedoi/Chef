package com.paul.chef.ui.menuEdit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.data.Dish
import com.paul.chef.databinding.ItemAddDishWithOrBinding
import com.paul.chef.databinding.ItemAddDishWithTitleBinding

class UserDishesAdapter : ListAdapter<Dish, RecyclerView.ViewHolder>(DishCallback()) {

    val WITHTITLE = 0
    val WITHOR = 1
    val title = "default"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 0) {
            WithTitleHolder.from(parent)
        } else {
            WithOrHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is WithTitleHolder -> {
                holder.bind(item)
            }
            is WithOrHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        if (title != item.type) {
            return WITHTITLE
            title = item.type
        } else {
            return WITHOR
        }

    }


    class WithTitleHolder(private var binding: ItemAddDishWithTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dish) {
            binding.type.text = item.type
            binding.titleDishName.text = item.name

            if (item.option == 3) {
                binding.titlePrice.text = item.extraPrice.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): WithTitleHolder {
                val binding = ItemAddDishWithTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WithTitleHolder(binding)
            }
        }
    }

    class WithOrHolder(private var binding: ItemAddDishWithOrBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dish) {
            binding.orDishName.text = item.name

            if (item.option == 3) {
                binding.orPrice.text = item.extraPrice.toString()
            }

        }

        companion object {
            fun from(parent: ViewGroup): WithOrHolder {
                val binding = ItemAddDishWithOrBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WithOrHolder(binding)
            }
        }
    }

    class DishCallback : DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }


    }
}
