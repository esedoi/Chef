package com.paul.chef.ui.chatList

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoChatRoom
import com.paul.chef.Mode
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.data.Room
import com.paul.chef.databinding.ItemChatListBinding
import com.paul.chef.ui.menuDetail.bindImage
import java.util.*

class ChatListAdapter(private val goChatRoom: GoChatRoom, private val nowMode: Int) :
    ListAdapter<Room, RecyclerView.ViewHolder>(ChatListCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RoomHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is RoomHolder) {
            holder.bind(item, nowMode, goChatRoom)
        }
    }

    class RoomHolder(private var binding: ItemChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Room, nowMode: Int, goChatRoom: GoChatRoom) {
            itemView.setOnClickListener {
                goChatRoom.goChatRoom(item.id)
            }

            val outlineProvider = ProfileOutlineProvider()
            binding.userImage.outlineProvider = outlineProvider

            val sdf = SimpleDateFormat("MM.dd HH:mm")
            if (item.time != null) {
                binding.time.text = sdf.format(Date(item.time.toLong()))
            }

            binding.chatContent.text = item.lastMsg.toString()
            if (nowMode == Mode.CHEF.index) {
                binding.userName.text = item.userName
                bindImage(binding.userImage, item.userAvatar)
            } else {
                binding.userName.text = item.chefName
                bindImage(binding.userImage, item.chefAvatar)
            }
        }

        companion object {
            fun from(parent: ViewGroup): RoomHolder {
                val menu =
                    ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RoomHolder(menu)
            }
        }
    }
}

class ChatListCallback : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}
