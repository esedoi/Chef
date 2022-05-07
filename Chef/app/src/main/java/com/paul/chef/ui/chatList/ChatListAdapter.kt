package com.paul.chef.ui.chatList

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoChatRoom
import com.paul.chef.ItemMenu
import com.paul.chef.Mode
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.Room
import com.paul.chef.databinding.ItemChatListBinding
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.ui.menuDetail.bindImage
import java.util.*

class ChatListAdapter(val goChatRoom: GoChatRoom, private val nowMode:Int) : ListAdapter<Room, RecyclerView.ViewHolder>(FriendListCallback()) {


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
            binding.userImage.outlineProvider =outlineProvider
            Log.d("chatlistAdapter", "${item.time}")

            val sdf = SimpleDateFormat("MM.dd HH:mm")
            binding.time.text = sdf.format(item.time?.let { Date(it.toLong()) })

            binding.chatContent.text = item.lastMsg.toString()
            if(nowMode == Mode.CHEF.index){
                binding.userName.text = item.userName
                bindImage(binding.userImage, item.userAvatar)
            }else{
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

class FriendListCallback : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
