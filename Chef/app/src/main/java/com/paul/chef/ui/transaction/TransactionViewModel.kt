package com.paul.chef.ui.transaction

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

import com.google.gson.Gson
import com.paul.chef.*
import com.paul.chef.data.Order

import com.paul.chef.data.Transaction

class TransactionViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()

    private val unpaidList = mutableListOf<Order>()
    private val processingList = mutableListOf<Transaction>()
    private val receivedList = mutableListOf<Transaction>()


    private var _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private var _transactionList = MutableLiveData<List<Transaction>>()
    val transactionList: LiveData<List<Transaction>>
        get() = _transactionList


    val chefId = ChefManger().chefId




    fun getList(position:Int){

        db.collection("Transaction")
            .whereEqualTo( "chefId" , chefId)
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    processingList.clear()
                    receivedList.clear()
                    for (item in value.documents){
                        val item = item.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Transaction::class.java)
                        when(data.status){
                            TransactionStatus.PROCESSING.index->{
                                processingList.add(data)
                            }
                            TransactionStatus.COMPLETED.index->{
                                receivedList.add(data)
                            }
                        }
                    }

                    if(position==1){
                        _transactionList.value = processingList
                    }
                    if(position==2){
                        _transactionList.value = receivedList
                    }
                } else {
                  //沒資料
                }
            }
            .addOnFailureListener { exception ->
                Log.d("transactionviewmodel", "get failed with ", exception)
            }



        db.collection("Order")
            .whereEqualTo( "chefId" , chefId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                unpaidList.clear()
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Order::class.java)
                    Log.d("transactionviewmodel", "data =$data ")
                    when(data.status){
                        OrderStatus.COMPLETED.index, OrderStatus.SCORED.index->{
                            unpaidList.add(data)
                        }
                    }
                }
                if(position==0){
                    _orderList.value = unpaidList
                }
            }

    }

//    data class Transaction(
//        val id:	String,
//        val time:Long,
//        val chefReceive:Int,
    //    val orderList:list<String>
//        val status:Int, //處理中, 已付款
//    )


    fun applyMoney(){
//        val id = db.collection("Transaction").document().id
//        val transaction = Transaction(id, )
//
//        db.collection("Transaction").document(id)
//            .set(room)
//            .addOnSuccessListener { documentReference ->
//                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
//                _roomId.value = id
//            }
//            .addOnFailureListener { e ->
//                Log.w("click", "Error adding document", e)
//            }

    }

}