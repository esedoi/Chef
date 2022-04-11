package com.paul.chef.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate


data class Chef(
    val id:	String,
    val profileInfo:ProfileInfo,
    val bankInfo:BankInfo?=null,
    val address:List<String>
)

data class OrderSetting(
    val type:Int = -1, //接受user前來廚房,接受前往user廚房,都接受, 都不接受
    val defaultTime:DefaultTime,
    val override:Override?=null,
    val capacity:Int,
    val duration:String,
)

@Parcelize
data class ChefMenu( ///menu
    val id:String,
    val images:List<String>,
    val dishes:@RawValue List<Dish>,
    val chefId:String,
    val reviewRating: Float? = null,
    val perPrice:Int,
    val discount:@RawValue List<Discount>,
    val orderSetting: @RawValue OrderSetting
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
    val dishName:@RawValue List<DishName>
): Parcelable

data class DishName(
    val extraPrice:Int,
    val name:String
)
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



data class DefaultTime(
    val default:Boolean, //all open, all close
    val startTime:String, //11:30
    val endTime:String, //22:30
)

data class Override(
    val week:List<WeekStatus>, //一二三四五六日
    val date:List<DateStatus>, // 5/16, 6/13
)

data class DateStatus(
    val status:Boolean,
    val date:String,
    val startTime:String,
    val endTime:String,
)

data class WeekStatus(
    val status:Boolean,
    val week:String, //一二三四五六日
    val startTime:String,
    val endTime:String,
)


data class OrderTable(
    val orderId:String, //D2204130685964
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
    val total:Int
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


