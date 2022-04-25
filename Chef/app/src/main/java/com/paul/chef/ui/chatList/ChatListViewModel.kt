package com.paul.chef.ui.chatList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.data.Chat
import com.paul.chef.data.Room


class ChatListViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()




    init {


    }
}