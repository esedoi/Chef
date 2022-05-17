package com.paul.chef.ui.chatList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.*
import com.paul.chef.data.Room
import com.paul.chef.databinding.FragmentChatListBinding


class ChatListFragment : Fragment(), GoChatRoom {


    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatListAdapter: ChatListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null


    val db = FirebaseFirestore.getInstance()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val chatListViewModel =
            ViewModelProvider(this)[ChatListViewModel::class.java]

        val roomList = mutableListOf<Room>()
        var nowMode = -1

        val mode = UserManger.readData("mode", (activity as MainActivity))
        when (mode) {
            Mode.USER.index -> {
                binding.topAppBar.title = getString(R.string.message_from_chef)
            }
            Mode.CHEF.index -> {
                binding.topAppBar.title = getString(R.string.massage_from_customer)
            }
        }

        if (mode != null) {
            nowMode = mode
        }

        chatListAdapter = ChatListAdapter(this, nowMode)
        layoutManager = LinearLayoutManager(this.context)
        binding.chatListRecycler.layoutManager = layoutManager
        binding.chatListRecycler.adapter = chatListAdapter

        chatListViewModel.roomList.observe(viewLifecycleOwner) {
            it.sortedBy { sort ->
                sort.time
            }
            roomList.clear()
            roomList.addAll(it)


            when {
                roomList.isEmpty() && mode == Mode.USER.index -> {
                    binding.chatUserEmptySticker.visibility = View.VISIBLE
                    binding.chatEmptyText.visibility = View.VISIBLE
                    binding.chatChefEmptySticker.visibility = View.GONE
                }
                roomList.isEmpty() && mode == Mode.CHEF.index -> {
                    binding.chatUserEmptySticker.visibility = View.GONE
                    binding.chatEmptyText.visibility = View.VISIBLE
                    binding.chatChefEmptySticker.visibility = View.VISIBLE
                }
                roomList.isNotEmpty() && mode == Mode.USER.index -> {
                    binding.chatUserEmptySticker.visibility = View.GONE
                    binding.chatEmptyText.visibility = View.GONE
                    binding.chatChefEmptySticker.visibility = View.GONE
                }
                roomList.isNotEmpty() && mode == Mode.CHEF.index -> {
                    binding.chatUserEmptySticker.visibility = View.GONE
                    binding.chatEmptyText.visibility = View.GONE
                    binding.chatChefEmptySticker.visibility = View.GONE
                }
            }
            chatListAdapter.submitList(roomList)
            chatListAdapter.notifyDataSetChanged()
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