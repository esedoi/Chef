package com.paul.chef.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate


data class Chef(
    val id:	String,
    val profileInfo:ProfileInfo,
    val bankInfo:BankInfo?=null,
    val address:List<String>?=null,
    val bookSetting: BookSetting? = null
)

//come with menu
@Parcelize
data class BookSetting(
    val type:Int = -1, //chef place,userplace,都接受, 都不接受
    val calendarDefault:Int, //all open, all close
    val chefSpace: @RawValue ChefSpace? = null,
    val userSpace: @RawValue UserSpace? = null,
    val dateSetting:@RawValue DateSetting? = null,
): Parcelable

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
    val week:List<WeekStatus>?=null, //一二三四五六日
    val date:List<DateStatus>?=null, // 5/16, 6/13
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
    val menuList:List<MenuStatus>? = null,
    val todayPeople:Int? =  null
)

data class Order(
    val id:String, //D2204130685964
    val userId:String,
    val chefId:String, //chefId or kitchenId
    val type:Int, //userspace, chef space
    val address: String,
    val orderTime:Long,
    val date:Long,
    val time:String,
    val note:String,
    val people:Int,
    val menuId:String,
    val selectedDish: List<Dish>,
    val status:Int, //即將到來,已取消,已完成
    val originalPrice:Int,
    val discount:Int,
    val total:Int,
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
    val typeNumber:Int = -1
): Parcelable

@Parcelize
data class DisplayDish(
    val type:String, //甜點、開胃菜
    val option:Int, //固定菜色, 可替換, 加價替換
    val name:String? = "",
    val extraPrice:Int? = -1,
    val displayType:Int
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

@Parcelize
data class SelectedDates(
    val selectedDates: List<LocalDate>
):Parcelable

@Parcelize
data class SelectedDate(
    val selectedDate: LocalDate
):Parcelable




