package com.paul.chef.ui.chatRoom

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.MainActivity
import com.paul.chef.Mode
import com.paul.chef.UserManger
import com.paul.chef.data.Chat
import com.paul.chef.data.Room
import com.paul.chef.databinding.ItemLeftChatBinding
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.databinding.ItemRightChatBinding

class ChatRoomAdapter(val nowId:String) : ListAdapter<Chat, RecyclerView.ViewHolder>(FriendListCallback()) {

    private val LEFT = 0
    private val RIGHT = 1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            0-> LeftHolder.from(parent)
            else ->RightHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)


        when (holder) {
            is LeftHolder ->{
                holder.bind(item)
            }
            is RightHolder->{
                holder.bind(item)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return when(getItem(position).senderId){
            nowId -> RIGHT
            else -> LEFT
        }

    }


    class LeftHolder(private var binding: ItemLeftChatBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Chat) {
            binding.message.text = item.message
        }

        companion object {
            fun from(parent: ViewGroup): LeftHolder {
                val msg =
                    ItemLeftChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return LeftHolder(msg)
            }
        }
    }

    class RightHolder(private var binding: ItemRightChatBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Chat) {
            binding.message.text = item.message
        }

        companion object {
            fun from(parent: ViewGroup): RightHolder {
                val msg =
                    ItemRightChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RightHolder(msg)
            }
        }
    }


}

class FriendListCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
