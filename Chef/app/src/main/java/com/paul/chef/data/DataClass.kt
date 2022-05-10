package com.paul.chef.data

import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate


data class Chef(
    val id:	String,
    val profileInfo:ProfileInfo,
    val bankInfo:BankInfo?=null,
    val address:List<Address>?=null,
    val bookSetting: BookSetting? = null,
    val reviewRating: Float?=null,
    val reviewNumber:Int? = null
)


@Parcelize
data class BookSetting(
    val type:Int = -1,
    val calendarDefault:Int, //all open, all close
    val chefSpace: @RawValue ChefSpace? = null,
    val userSpace: @RawValue UserSpace? = null,
    val dateSetting:@RawValue DateSetting? = null,
): Parcelable

data class MenuStatus(
    val menuId:String? = null,
    val status:Int?=null
)


data class ChefSpace(
    val sessionCapacity:Int,
    val session:List<String>,
    val address:Address
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

@Parcelize
data class Order(
    val id:String, //D2204130685964
    val userId:String,
    val chefId:String, //chefId or kitchenId
    val userName:String,
    val chefName:String,
    val userAvatar:String,
    val chefAvatar:String,
    val menuName:String,
    val type:Int, //userspace, chef space
    val address: Address,
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
    val userPay:Int,
    val chefReceive:Int
): Parcelable


@Parcelize
data class Address(
    val addressTxt:String,
    val latitude:Double,
    val longitude:Double
): Parcelable

@Parcelize
data class Menu(
    val id:String,
    val chefId:String,
    val menuName:String,
    val chefName:String,
    val chefAvatar: String,
    val intro:String,
    val perPrice:Int,
    val images:List<String>,
    val discount:@RawValue List<Discount>,
    val dishes:@RawValue List<Dish>,
    val reviewRating: Float? = null,
    val reviewNumber:Int?=null,
    val tagList:List<String>?=null,
    val open:Boolean
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
data class Review(
    val rating:Float,
    val userId:String,
    val userName:String,
    val userAvatar:String,
    val content:String,
    val date:Long
): Parcelable

@Parcelize
data class User(
    val userId:	String?=null,
    val profileInfo:ProfileInfo?=null,
    val chefId:String?=null,
    val likeList:List<String>?=null,
    val address:@RawValue List<Address>?=null,
    var blockMenuList:List<String>?=null,
    var blockReviewList: List<String>?=null

): Parcelable

@Parcelize
data class ProfileInfo(
    val name: String,
    val email:String,
    val avatar: String? =null,
    val introduce: String?=null
): Parcelable


data class BankInfo(
    val bank:String,
    val bankAccount:Long,
    val accountName:String
)

data class Room(
    val id: String,
    val attendance: List<String>, //userId,chefId
    val userName: String,
    val userAvatar: String,
    val chefName: String,
    val chefAvatar: String,
    val lastMsg: String?=null,
    val dataType: Int?=null,  //0String, 1image,
    val time: Long?=null, //用來排序
)


data class Chat(
    val message: String,
    val dataType: Int,  //0String, 1image,
    val senderId: String, //userId,chefId
    val time: Long, //用來排序
)



data class Transaction(
    val id:	String,
    val chefId:String,
    val time:Long,
    val chefReceive:Int,
    val orderList:List<String>,
    val status:Int, //處理中, 已付款
)

@Parcelize
data class SelectedDates(
    val selectedDates: List<LocalDate>
):Parcelable

@Parcelize
data class SelectedDate(
    val selectedDate: LocalDate
):Parcelable




