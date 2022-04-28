package com.paul.chef.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.ItemMenu
import com.paul.chef.MenuType
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.databinding.ItemMenuSimpleBinding
import com.paul.chef.ui.chatRoom.ChatRoomAdapter

class MenuListAdapter(private val itemMenu:ItemMenu?, menuViewModel:ViewModel?, val type:Int) : ListAdapter<ChefMenu, RecyclerView.ViewHolder>(MenuCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return FullHolder.from(parent)
        return when(type){
            MenuType.FULL.index-> FullHolder.from(parent)
            else -> SimpleHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)


        if (holder is FullHolder) {
            holder.bind(item, itemMenu)
        }

    }

//    override fun getItemViewType(position: Int): Int {
//
//
//        return super.getItemViewType(position)
//    }


    class FullHolder(private var binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ChefMenu, itemMenu: ItemMenu?) {
            if(itemMenu!=null){
                itemView.setOnClickListener {
                    itemMenu.goDetail(item)
                }
                binding.itemMenuLikeBtn.setOnClickListener {
                    itemMenu.like(item.id)
                }
                binding.menuTitle.text = item.menuName
            }
        }

        companion object {
            fun from(parent: ViewGroup): FullHolder {
                val menu =
                    ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FullHolder(menu)
            }
        }
    }


    class SimpleHolder(private var binding: ItemMenuSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChefMenu, itemMenu: ItemMenu) {
            itemView.setOnClickListener {
                itemMenu.goDetail(item)
            }

            binding.itemMenuSimpleTitle.text = item.menuName
        }

        companion object {
            fun from(parent: ViewGroup): SimpleHolder {
                val menu =
                    ItemMenuSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SimpleHolder(menu)
            }
        }
    }




}

class MenuCallback : DiffUtil.ItemCallback<ChefMenu>() {
    override fun areItemsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}
