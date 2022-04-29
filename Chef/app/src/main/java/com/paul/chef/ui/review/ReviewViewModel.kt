package com.paul.chef.ui.review

import android.annotation.SuppressLint
import android.app.Application
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.OrderStatus
import com.paul.chef.data.Chef
import com.paul.chef.data.Order
import com.paul.chef.data.Review
import java.util.*

class ReviewViewModel(application: Application) : AndroidViewModel(application){
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()

    fun rating(txt:String, rating:Float, order:Order){
        val orderId = order.id
        val status = OrderStatus.SCORED.index
        val menuId = order.menuId
        val chefId = order.chefId
        var newChefRating:Float= 0F
        var newChefRatingNumber = 0
        var newMenuRating:Float= 0F
        var newMenuRatingNumber = 0

        db.collection("Order").document(orderId)
            .update(mapOf(
                "status" to status
            ))
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }



        db.collection("Chef")
            .document(chefId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("pickerViewModel", "DocumentSnapshot data: ${document.data}")
                    val item = document.data
                    val a:Long = document.data?.get("ratingNumber") as Long
                    val b:Long = document.data?.get("reviewRating") as Long
                    Log.d("reviewviewmodel", "a=$a, b=$b")
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    val chefRatingNumber = data.ratingNumber?:0
                    val chefRating = (data.reviewRating?:0) as Float
                    newChefRatingNumber = chefRatingNumber+1
                    newChefRating = ((chefRating*chefRatingNumber)+rating)/newChefRatingNumber

                    db.collection("Chef").document(chefId)
                        .update(mapOf(
                            "reviewRating" to newChefRating,
                            "reviewNumber" to newChefRatingNumber
                        ))
                        .addOnSuccessListener { documentReference ->
                            Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("click", "Error adding document", e)
                        }

                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }


        db.collection("Menu")
            .document(menuId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("pickerViewModel", "DocumentSnapshot data: ${document.data}")
                    val item = document.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Chef::class.java)
                    val menuRatingNumber= data.ratingNumber?:0
                    val menuRating = (data.reviewRating?:0) as Float
                    newMenuRatingNumber = menuRatingNumber+1
                    newMenuRating = ((menuRating*menuRatingNumber)+rating)/newMenuRatingNumber

                    db.collection("Menu").document(menuId)
                        .update(mapOf(
                            "reviewRating" to newMenuRating,
                            "reviewNumber" to newMenuRatingNumber
                        ))
                        .addOnSuccessListener { documentReference ->
                            Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("click", "Error adding document", e)
                        }

                } else {
                    Log.d("pickerViewModel", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("pickerViewModel", "get failed with ", exception)
            }



        val id = db.collection("Review").document().id
        val date = Calendar.getInstance().timeInMillis
        val review = Review(rating, order.userId, order.userName, order.userAvatar, txt, date)
        db.collection("Menu").document(menuId)
            .collection("Review").document(id)
            .set(review)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }
}