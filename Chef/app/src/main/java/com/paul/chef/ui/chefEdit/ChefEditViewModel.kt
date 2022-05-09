package com.paul.chef.ui.chefEdit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.UserManger
import com.paul.chef.data.*

class ChefEditViewModel(application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private var _userId = MutableLiveData<String>()
    val userId: LiveData<String>
        get() = _userId

    private var _getUserDone = MutableLiveData<Boolean>()
    val getUserDone: LiveData<Boolean>
        get() = _getUserDone




    private val db = FirebaseFirestore.getInstance()

//    fun createChef(name:String, email:String, intro:String){
//        val id = db.collection("Chef").document().id
//        val profileInfo = ProfileInfo(name, email, "imagesString",intro)
//
//        val temp = Chef(id, profileInfo)
//
//
//        //set firebase資料
//        db.collection("Chef").document(id)
//            .set(temp)
//            .addOnSuccessListener { documentReference ->
//                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
//                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e ->
//                Log.w("click", "Error adding document", e)
//            }
//
//    }

//    fun saveChef(chefId:String, name:String, email:String, intro:String){
//
//        val info = ProfileInfo(name, email, "jackimage",intro)
//
//        db.collection("Chef").document(chefId)
//            .update(mapOf(
//                "profileInfo" to info,
//            ))
//            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")
//                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }
//
//    }

    fun getUser(userId:String){
        db.collection("User")
            .document(userId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (value != null) {
                        val json = Gson().toJson(value.data)
                        val data = Gson().fromJson(json, User::class.java)
                        UserManger.user = data
                    _getUserDone.value = true
                }
            }
    }



        fun saveChef(profileInfo: ProfileInfo, userId:String, chefId:String){

        val info = ProfileInfo(profileInfo.name, profileInfo.email, profileInfo.avatar, profileInfo.introduce)

        db.collection("Chef").document(chefId)
            .update(mapOf(
                "profileInfo" to info,
            ))
            .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

            db.collection("User").document(userId)
                .update(mapOf(
                    "profileInfo" to info,
                ))
                .addOnSuccessListener { Log.d("notification", "DocumentSnapshot successfully updated!")
                    Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e -> Log.w("notification", "Error updating document", e) }

    }


    fun createUser(profileInfo: ProfileInfo){

        val id = db.collection("User").document().id
        val temp = User(id, profileInfo)
        //set firebase資料
        db.collection("User").document(id)
            .set(temp)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                _userId.value = id
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }

}


