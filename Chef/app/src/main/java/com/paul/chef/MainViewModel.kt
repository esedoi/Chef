package com.paul.chef

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result

import kotlinx.coroutines.launch


class MainViewModel(private val repository: ChefRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()


    private var _chefId = MutableLiveData<String>()
    val chefId: LiveData<String>
        get() = _chefId

    private var _newUser = MutableLiveData<Boolean>()
    val newUser: LiveData<Boolean>
        get() = _newUser

    private var _isChef = MutableLiveData<Boolean>()
    val isChef: LiveData<Boolean>
        get() = _isChef


//    init {
//        if (UserManger.user?.userId != null) {
//
//            db.collection("User")
//                .document(UserManger.user?.userId ?: "")
//                .addSnapshotListener { document, e ->
//                    if (e != null) {
//                        Log.w("notification", "Listen failed.", e)
//                        return@addSnapshotListener
//                    }
//                    if (document != null) {
//                        val json = Gson().toJson(document.data)
//                        val data = Gson().fromJson(json, User::class.java)
//                        UserManger.user = data
//                        Log.d("mainviewmodel", "update user")
//                        if (data.chefId != null) {
//                            getChef(data.chefId)
//                        }
//                    }
//                }
//        }
//    }

    fun getUser(email: String) {
        viewModelScope.launch {
            val result = repository.getUserByEmail(email)
            Log.d("result", "result=$result")
            when(result){
                is Result.Success->{
                    _newUser.value = result.data==null
                    if(result.data?.chefId !=null){
                        getChef(result.data.chefId)
                    }
                }
                else -> {
                }
            }
        }

    }


    private fun getChef(chefId: String) {

        repository.getLiveChef(chefId)

//        viewModelScope.launch {
//            when(val result = repository.getLiveChef(chefId)){
//                is Result.Success->{
//                    UserManger.chef = result.data
//                }
//            }
//        }
    }


    fun block(userId: String, blockMenuList: List<String>?, blockReviewList: List<String>?) {

        viewModelScope.launch {
            if (blockMenuList != null) {
                repository.blockMenu(blockMenuList,userId)
            }
            if (blockReviewList != null) {
                repository.blockReview(blockReviewList,userId)
            }
        }
    }
}