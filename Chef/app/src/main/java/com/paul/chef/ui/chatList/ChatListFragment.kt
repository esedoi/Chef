package com.paul.chef.ui.chatList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.*
import com.paul.chef.data.Chat
import com.paul.chef.data.Room
import com.paul.chef.databinding.FragmentChatListBinding
import com.paul.chef.ui.chef.ChefViewModel

class ChatListFragment : Fragment(),GoChatRoom {


    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    lateinit var chatListAdapter: ChatListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var viewModel: ChatListViewModel

    val db = FirebaseFirestore.getInstance()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val chatListViewModel =
            ViewModelProvider(this).get(ChatListViewModel::class.java)

        val roomList = mutableListOf<Room>()
        var nowId = ""
        var nowMode =-1

        val mode = UserManger.readData("mode", (activity as MainActivity))
        nowId = when(mode){
            Mode.USER.index-> UserManger().userId
            Mode.CHEF.index-> ChefManger().chefId
            else->""
        }
        if(mode!=null){
            nowMode = mode
        }

        chatListAdapter = ChatListAdapter(this,nowMode)
        layoutManager = LinearLayoutManager(this.context)
        binding.chatListRecycler.layoutManager = layoutManager
        binding.chatListRecycler.adapter = chatListAdapter

        db.collection("Room")
            .whereArrayContains("attendance", nowId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                roomList.clear()
                if (value != null) {
                    for(document in value.documents){
                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Room::class.java)
                        roomList.add(data)
                    }

                    roomList.sortBy { it.time }
                    chatListAdapter.submitList(roomList)
                    chatListAdapter.notifyDataSetChanged()

                }
            }




















        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goChatRoom(roomId: String) {
        findNavController().navigate(MobileNavigationDirections.actionGlobalChatRoomFragment(roomId))
    }

}