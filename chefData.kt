

class Sample {
}

data class ChefData(
    val id:	String,
    val name: String,
    val avatar:String,
    val images:	List<String>,
    val introduce: String,
    val kitchen: List<String>,
    val roomList:RoomList,
    val acceptOrder: Boolean,
    val pricePlan:List<PricePlan>,
    val openTime:OpenTime,
    val star:Double,
    val review:List<ReView>,
    val orderNumber:Int,
    val bank:String,
    val bankAccount:Long,
    val accountName:String
)

data class RoomList(

)

data class PricePlan(
    val name:String,
    val price:Int
        )

data class ReView(
    val star:Double,
    val name:String,
    val content:String
)

data class OpenTime(

)

data class KitchenTable(
    val id: String,
    val name: String,
    val mainChef:String,
    val coChef:	List<String>,
    val avatar:String,
    val images:	List<String>,
    val introduce: String,
    val address:Address,
    val pricePlan:PricePlan,
    val roomList:RoomList,
    val openTime:OpenTime,
    val star:Double,
    val review:List<ReView>,
    val orderNumber:Int,
    val bank:String,
    val bankAccount:Long,
    val accountName:String
)

data class UserTable(
    val id:	String,
    val avatar:String,
    val name:String,
    val intro: String,
    val star: Double,
    val review:List<ReView>,
    val orderNumber:Int,
    val roomList:RoomList,
)

data class orderTable(
    val id:String,
    val userName:String,
    val type:String,
    val people:Int
    val time:Long,
    val plan:Plane,
    val status:Int,
    val orginalPrice:Int,
    val discount:String,
    val total:Int
)

data class payTable(
    val id:	String,
    val type:Int,
    val time:Long,
    val price:Int,
    val email:String
    val account:Long
    val bank:String,
    val acountName:String
    val status:Int
)


