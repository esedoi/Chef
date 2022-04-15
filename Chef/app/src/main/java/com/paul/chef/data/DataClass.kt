package com.paul.chef.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class Chef(
    val id:	String,
    val profileInfo:ProfileInfo,
    val bankInfo:BankInfo?=null,
    val address:List<String>,
    val orderSetting: OrderSetting? = null
)

//come with menu
data class OrderSetting(
    val type:Int = -1, //chef place,userplace,都接受, 都不接受
    val defaultDateStatus:Int, //all open, all close
    val menuList:List<MenuStatus>? = null,
    val chefSpace:ChefSpace? = null,
    val userSpace:UserSpace? = null,
    val dateSetting:DateSetting? = null,
    val todayPeople:Int? =  null
)

data class MenuStatus(
    val menuId:String,
    val status:Int
)


data class ChefSpace(
    val sessionCapacity:Int,
    val session:List<String>,
)

data class UserSpace(
    val capacity:Int,
    val starTime:String,
    val endTime:String,
)

data class DateSetting(
    val week:List<WeekStatus>, //一二三四五六日
    val date:List<DateStatus>, // 5/16, 6/13
)

data class WeekStatus(
    val status:Int,
    val week:String, //一二三四五六日
    val session:List<String>? = null,
    val startTime:String? = null,
    val endTime:String? = null,
)

data class DateStatus(
    val status:Int,
    val date:Long,
    val session:List<String>? = null,
    val startTime:String? = null,
    val endTime:String? = null,
)

data class Order(
    val id:String, //D2204130685964
    val userName:String,
    val userId:String,
    val userEmail:String,
    val chefId:String, //chefId or kitchenId
//    val chefEmail:String,
    val date:Long,
//    val time:String,
    val note:String,
    val people:Int,
    val orderTime:Long,
    val menuId:String,
    val status:Int, //即將到來,已取消,已完成
    val originalPrice:Int,
    val discount:Int,
    val total:Int,
    val type:Int,
    val address: String
)

@Parcelize
data class ChefMenu( ///menu
    val id:String,
    val chefId:String,
    val menuName:String,
    val intro:String,
    val perPrice:Int,
    val images:List<String>,
    val discount:@RawValue List<Discount>,
    val dishes:@RawValue List<Dish>,
    val reviewRating: Float? = null,
): Parcelable

@Parcelize
data class Discount(
    val people:Int,
    val percentOff:Int
): Parcelable

@Parcelize
data class Dish(
    val type:String, //甜點、開胃菜
    val option:Int, //固定菜色, 可替換, 加價替換
    val name:String? = "",
    val extraPrice:Int? = -1,
): Parcelable

@Parcelize
data class ExtraDish(
    val type:String, //甜點、開胃菜
    val number:Int,
    val extraPrice:Int,
    val name:String
): Parcelable

@Parcelize
data class Review(
    val star:Double,
    val userId:String,
    val content:String
): Parcelable


data class User(
    val userId:	String,
    val profileInfo:ProfileInfo,
    val address:List<String>,
)


data class ProfileInfo(
    val name: String,
    val email:String,
    val avatar:String,
    val introduce: String
)


data class BankInfo(
    val bank:String,
    val bankAccount:Long,
    val accountName:String
)

data class Room(
    val id: String,
    val lastMsg: String,
    val dataType: Int,  //0String, 1image,
    val attendance: List<String>, //userId,chefId
    val chat:List<Chat>,
    val time: Long, //用來排序

)


data class Chat(
    val message: String,
    val dataType: Int,  //0String, 1image,
    val senderId: String, //userId,chefId
    val time: Long, //用來排序
)



data class VendorPayment(
    val id:	String,
    val type:Int,//chefId, kitchenId
    val time:Long,
    val price:Int,
    val email:String,
    val bankInfo:BankInfo,
    val status:Int, //處理中, 已付款, 已取消
)


