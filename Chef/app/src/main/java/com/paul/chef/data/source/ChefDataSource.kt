package com.paul.chef.data.source

import androidx.lifecycle.MutableLiveData
import com.paul.chef.data.*

interface ChefDataSource {

    suspend fun getMenuList(): Result<List<Menu>>

    fun getLiveMenuList(): MutableLiveData<List<Menu>>

    suspend fun setUser(profileInfo: ProfileInfo): Result<String>

    suspend fun getUserByEmail(email: String): Result<User?>

    suspend fun getUser(userId: String): Result<User>

    fun getLiveUser(): MutableLiveData<User>

    suspend fun getChef(id: String, isDisplay: Boolean): Result<Chef>

    fun getLiveChef(id: String, isDisplay:Boolean): MutableLiveData<Chef>

    suspend fun getChefIdList(settingType: List<Int>): Result<List<String>>

    suspend fun blockMenu(blockMenuList: List<String>, userId: String)

    suspend fun blockReview(blockReviewList: List<String>, userId: String)

    suspend fun createChef(user: User): Result<String>

    suspend fun updateProfile(profileInfo: ProfileInfo, userId: String, chefId: String?)

    suspend fun updateAddress(addressList: List<Address>)

    suspend fun setOrder(order: Order): Result<Boolean>

    suspend fun updateOrderStatus(status: Int, orderId: String):Result<Boolean>

    suspend fun updateChefReview(chefId: String, newChefRating: Float, newChefRatingNumber: Int)

    suspend fun updateMenuReview(menuId: String, newMenuRating: Float, newMenuRatingNumber: Int)

    suspend fun updateBookSetting(
        type: Int,
        calendarDefault: Int,
        chefSpace: ChefSpace?,
        userSpace: UserSpace?,
    )

    suspend fun getMenu(id: String): Result<Menu>

    suspend fun setReview(menuId: String, review: Review)

    suspend fun setRoom(
        userId: String,
        chefId: String,
        useName: String,
        chefName: String,
        userAvatar: String,
        chefAvatar: String,
    ): Result<String>

    suspend fun getRoom(userId: String, chefId: String): Result<String>

    fun getLiveRoomList(nowId: String): MutableLiveData<List<Room>>

    fun getLiveChat(roomId:String):MutableLiveData<List<Chat>>

    suspend fun updateRoom(roomId: String, msg: String, nowId: String, time: Long)

    suspend fun setChat(roomId: String, msg: String, nowId: String, time: Long)

    suspend fun setMenu(
        menuName: String,
        menuIntro: String,
        perPrice: Int,
        images: List<String>,
        discountList: List<Discount>,
        dishList: List<Dish>,
        tagList: List<String>,
        openBoolean: Boolean,
    )

    suspend fun updateLikeList(newList: List<String>)

    suspend fun getMenuReviewList(menuId: String): Result<List<Review>>

    fun getLiveChefDateSetting(chefId: String): MutableLiveData<List<DateStatus>>

    suspend fun setDateSetting(date: Long, status: Int)

    suspend fun getChefMenuList(chefId: String): Result<List<Menu>>

    fun getLiveOrder(field: String, value: String): MutableLiveData<List<Order>>

    suspend fun getTransaction(): Result<List<Transaction>>

    suspend fun setTransaction(transaction: Transaction)
}
