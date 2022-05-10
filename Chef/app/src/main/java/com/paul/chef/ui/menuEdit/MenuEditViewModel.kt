package com.paul.chef.ui.menuEdit

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.paul.chef.BookSettingType
import com.paul.chef.BookType
import com.paul.chef.UserManger
import com.paul.chef.data.*

class MenuEditViewModel (application: Application) : AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val db = FirebaseFirestore.getInstance()
    val chefId = UserManger.chef?.id!!
    val chefName = UserManger.chef?.profileInfo?.name!!
    val avatar = UserManger.chef?.profileInfo?.avatar!!

    private var _openBoolean = MutableLiveData<Boolean>()
    val openBoolean: LiveData<Boolean>
        get() = _openBoolean

    init {

            db.collection("Chef")
                .document(chefId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("pickerViewModel", "DocumentSnapshot data: ${document.data}")
                        val item = document.data
                        val json = Gson().toJson(item)
                        val data = Gson().fromJson(json, Chef::class.java)
                        _openBoolean.value = data.bookSetting!=null&&data.bookSetting.type!=BookSettingType.RefuseAll.index

                    } else {
                        Log.d("pickerViewModel", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("pickerViewModel", "get failed with ", exception)
                }

    }



    fun createMenu(menuName:String,
                   menuIntro:String,
                   perPrice:Int,
                   images:List<String>,
                   discountList:List<Discount>,
                   dishList:List<Dish>,
                   tagList:List<String>,
                   openBoolean: Boolean){
        val id = db.collection("Menu").document().id


        val menu = Menu(id, chefId, menuName,chefName,avatar, menuIntro, perPrice, images, discountList, dishList, tagList = tagList, open = openBoolean)

        //set firebase資料
        db.collection("Menu").document(id)
            .set(menu)
            .addOnSuccessListener { documentReference ->
                Log.d("click", "DocumentSnapshot added with ID: ${documentReference}")
                Toast.makeText(this.context, "送出成功", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("click", "Error adding document", e)
            }

    }


}