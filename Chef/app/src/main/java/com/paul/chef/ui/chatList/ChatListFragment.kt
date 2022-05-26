package com.paul.chef.ui.chatList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Room
import com.paul.chef.databinding.FragmentChatListBinding
import com.paul.chef.ext.getVmFactory

class ChatListFragment : Fragment(), GoChatRoom {

    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatListAdapter: ChatListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val chatListViewModel by viewModels<ChatListViewModel> { getVmFactory() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mode = UserManger.readData("mode") ?: -1

        setAppBarTitle(mode)

        chatListAdapter = ChatListAdapter(this, mode)
        layoutManager = LinearLayoutManager(this.context)
        binding.chatListRecycler.layoutManager = layoutManager
        binding.chatListRecycler.adapter = chatListAdapter

        chatListViewModel.roomList.observe(viewLifecycleOwner) {
            val roomList = it.sortedBy { sort ->
                sort.time
            }
            emptyHandle(roomList, mode)
            chatListAdapter.submitList(roomList)
            chatListAdapter.notifyDataSetChanged()
        }

        return root
    }

    private fun setAppBarTitle(mode: Int) {
        when (mode) {
            Mode.USER.index -> {
                binding.topAppBar.title = getString(R.string.message_from_chef)
            }
            Mode.CHEF.index -> {
                binding.topAppBar.title = getString(R.string.massage_from_customer)
            }
        }
    }

    private fun emptyHandle(roomList: List<Room>, mode: Int?) {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goChatRoom(roomId: String) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalChatRoomFragment(roomId)
        )
    }
}
