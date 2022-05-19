package com.paul.chef.data.source

import androidx.lifecycle.MutableLiveData
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.data.User

interface ChefRepository {

    suspend fun getMenuList(menuId:String):Result<List<Menu>>

    suspend fun getUserByEmail(id: String):Result<User?>

    suspend fun getChef(id: String):Result<Chef>

    fun getLiveChef(id:String): MutableLiveData<Chef>

    suspend fun blockMenu(blockMenuList:List<String>, userId:String)

    suspend fun blockReview(blockReviewList:List<String>, userId: String)

    suspend fun createChef(user:User):Result<String>

    suspend fun updateOrderStatus(status:Int, orderId:String)

    suspend fun  updateChefReview(chefId:String, newChefRating:Float, newChefRatingNumber:Int)

    suspend fun  updateMenuReview(menuId:String, newMenuRating:Float, newMenuRatingNumber:Int)

    suspend fun getMenu(id:String): Result<Menu>

    suspend fun setReview(menuId:String, review: Review)

    suspend fun setRoom(userId:String, chefId:String,useName:String, chefName:String, userAvatar:String, chefAvatar:String):Result<String>

    suspend fun getRoom(userId:String, chefId:String):Result<String>

}