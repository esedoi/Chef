package com.paul.chef.data.source

import androidx.lifecycle.MutableLiveData
import com.paul.chef.data.*

interface ChefRepository {

    suspend fun getMenuList():Result<List<Menu>>

    fun getLiveMenuList():MutableLiveData<List<Menu>>

    suspend fun getUserByEmail(email: String):Result<User?>

    fun getLiveUser():MutableLiveData<User>

    suspend fun getChef(id: String):Result<Chef>

    suspend fun getChefIdList(settingType:List<Int>):Result<List<String>>

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

    suspend fun setMenu(menuName:String,
                        menuIntro:String,
                        perPrice:Int,
                        images:List<String>,
                        discountList:List<Discount>,
                        dishList:List<Dish>,
                        tagList:List<String>,
                        openBoolean: Boolean)

    suspend fun updateLikeList(newList:List<String>)

    suspend fun getMenuReviewList(menuId:String):Result<List<Review>>


}