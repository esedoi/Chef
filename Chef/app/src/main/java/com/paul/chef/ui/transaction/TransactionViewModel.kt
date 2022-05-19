package com.paul.chef.ui.transaction


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.data.Transaction
import java.util.*

class TransactionViewModel : ViewModel() {


    private val db = FirebaseFirestore.getInstance()

    private val unpaidList = mutableListOf<Order>()
    val processingList = mutableListOf<Transaction>()
    val receivedList = mutableListOf<Transaction>()


    private var _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>>
        get() = _orderList

    private var _transactionList = MutableLiveData<List<Transaction>>()
    val transactionList: LiveData<List<Transaction>>
        get() = _transactionList


    val chefId = UserManger.chef?.id!!


    fun getList(position: Int) {

        db.collection("Transaction")
            .whereEqualTo("chefId", chefId)
            .get()
            .addOnSuccessListener { value ->
                if (value.documents.isNotEmpty()) {
                    processingList.clear()
                    receivedList.clear()
                    for (doc in value.documents) {
                        val item = doc.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Transaction::class.java)
                        when (data.status) {
                            TransactionStatus.PROCESSING.index -> {
                                processingList.add(data)
                            }
                            TransactionStatus.COMPLETED.index -> {
                                receivedList.add(data)
                            }
                        }
                        if (value.documents.indexOf(doc) == value.documents.size - 1) {

                            when (position) {
                                1 -> {
                                    Log.d("transaction_view_model", "posiotion1")
                                    _transactionList.value = processingList
                                }
                                2 -> {
                                    Log.d("transaction_view_model", "posiotion2")
                                    _transactionList.value = receivedList
                                }
                            }
                        }
                    }

                } else {
                    //沒資料
                }
            }
            .addOnFailureListener { exception ->
                Log.d("transactionviewmodel", "get failed with ", exception)
            }



        db.collection("Order")
            .whereEqualTo("chefId", chefId)
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
                    when (data.status) {
                        OrderStatus.COMPLETED.index, OrderStatus.SCORED.index -> {
                            unpaidList.add(data)
                        }
                    }

                    if (value.documents.indexOf(doc) == value!!.documents.size - 1) {
                        if (position == 0) {
                            _orderList.value = unpaidList
                        }
                    }
                }
            }
    }

    fun changeStatus(orderId: String, status: Int) {
        //set firebase資料
        db.collection("Order").document(orderId)
            .update(
                mapOf(
                    "status" to status,
                )
            )
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }


    fun applyMoney(chefReceive: Int) {
        val id = db.collection("Transaction").document().id
        val time = Calendar.getInstance().timeInMillis
        var idList = mutableListOf<String>()


        for (i in unpaidList) {
            idList.add(i.id)
        }

        if (idList.isNotEmpty() && chefReceive != 0) {

            val transaction = Transaction(
                id,
                chefId,
                time,
                chefReceive,
                idList,
                TransactionStatus.PROCESSING.index
            )
            Log.d("transactionviewmodel", "transaction=$transaction")

            db.collection("Transaction").document(id)
                .set(transaction)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "transactionviewmodel",
                        "DocumentSnapshot added with ID: ${documentReference}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w("transactionviewmodel", "Error adding document", e)
                }
        }

    }

}