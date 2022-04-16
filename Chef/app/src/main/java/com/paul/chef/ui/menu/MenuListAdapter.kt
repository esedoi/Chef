package com.paul.chef.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.MenuDetail
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.ItemMenuListBinding

class MenuListAdapter(val menuDetail:MenuDetail) : ListAdapter<ChefMenu, RecyclerView.ViewHolder>(FriendListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticlesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)


        if (holder is ArticlesHolder) {
            holder.bind(item, menuDetail)
        }

    }


    class ArticlesHolder(private var binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ChefMenu, menuDetail: MenuDetail) {
            itemView.setOnClickListener {
                menuDetail.goDetail(item)
            }
            binding.menuTitle.text = item.menuName
        }

        companion object {
            fun from(parent: ViewGroup): ArticlesHolder {
                val menu =
                    ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ArticlesHolder(menu)
            }
        }
    }

}

class FriendListCallback : DiffUtil.ItemCallback<ChefMenu>() {
    override fun areItemsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
