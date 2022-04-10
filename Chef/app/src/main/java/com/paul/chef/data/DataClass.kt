package com.paul.chef.data


data class TestData(
    val chefId:String,
    val chefInfo:Info,
    val menuId: List<String>?,
    val address: List<String>
)


data class ChefData(
    val chefId:	String,
    val info:Info,
    val menuId: List<String>?=null,
//    val roomList:List<Room>, //check
    val bankInfo:BankInfo?=null,
//    val orderId:List<String>,  //check
    val orderSetting:OrderSetting,
    val address:List<String>
)

data class OrderSetting(
    val type:Int = -1, //接受user前來廚房,接受前往user廚房,都接受, 都不接受
    val defaultTime:DefaultTime,
    val override:Override?=null,
    val capacity:Int,
    val duration:String,
)


data class Menu(
    val menuId:String,
    val images:List<String>,
    val dishes:List<Dish>,
    val chefId:String,
    val review:List<Review>,
    val perPrice:Int,
    val discount:Discount,
)

data class Discount(
    val people:Int,
    val percentOff:Int
)

data class Dish(
    val type:String,
    val option:Int, //固定菜色, 可替換, 加價替換
    val dishName:List<DishName>
)

data class DishName(
    val extraPrice:Int,
    val name:String
)

data class Review(
    val star:Double,
    val userId:String,
    val content:String
)


data class UserTable(
    val userId:	String,
    val info:Info,
    val address:List<String>,
    val orderId:List<String>,
    val roomList:List<Room>,
)


data class Info(
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
    val lastMsg: String,
    val dataType: Int,  //0Strng, 1image,
    val senderId: String, //userId,chefId
    val time: Long, //用來排序
    val roomID: String
)


data class Chat(
    val message: String,
    val dataType: Int,  //0Strng, 1image,
    val senderId: String, //userId,chefId
    val time: Long, //用來排序
    val roomID: String
)


data class ReView(
    val star:Double,
    val name:String,
    val content:String
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
    val chefEmail:String,
    val people:Int,
    val time:Long,
    val menuId:String,
    val status:Int, //即將到來,已取消,已完成
    val originalPrice:Int,
    val discount:String,
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


