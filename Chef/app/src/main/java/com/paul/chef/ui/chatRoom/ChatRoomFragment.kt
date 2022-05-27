package com.paul.chef.ui.chatRoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.*
import com.paul.chef.data.Chat
import com.paul.chef.databinding.FragmentChatRoomBinding
import com.paul.chef.ext.getVmFactory

class ChatRoomFragment : Fragment() {

    private var _binding: FragmentChatRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatRoomAdapter: ChatRoomAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val arg: ChatRoomFragmentArgs by navArgs()

    private val chatList = mutableListOf<Chat>()

    private val chatRoomViewModel by viewModels<ChatRoomViewModel> { getVmFactory() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val roomId = arg.roomId

        val nowId: String

        val mode = UserManger.readData("mode")
        nowId = when (mode) {
            Mode.USER.index -> UserManger.user?.userId!!
            Mode.CHEF.index -> UserManger.chef?.id!!
            else -> ""
        }

        chatRoomAdapter = ChatRoomAdapter(nowId)
        layoutManager = LinearLayoutManager(this.context)
        binding.chatRoomRecycler.layoutManager = layoutManager
        binding.chatRoomRecycler.adapter = chatRoomAdapter

        chatRoomViewModel.getLiveChat(roomId)
        chatRoomViewModel.chatList.observe(viewLifecycleOwner) {
            chatList.clear()
            chatList.addAll(it)
            chatRoomAdapter.submitList(chatList)
            chatRoomAdapter.notifyDataSetChanged()
            binding.chatRoomRecycler.scrollToPosition(chatList.size - 1)
        }

        binding.chatRoomSendBtn.setOnClickListener {
            val msg = binding.editText.editText?.text.toString()
            if (nowId != "" && msg != "") {
                chatRoomViewModel.sendMsg(roomId, msg, nowId)
                binding.editText.editText?.setText("")
                binding.chatRoomRecycler.scrollToPosition(chatList.size - 1)
            }
        }

        return root
    }
}
