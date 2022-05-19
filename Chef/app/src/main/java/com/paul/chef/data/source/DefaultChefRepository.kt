package com.paul.chef.data.source

import androidx.lifecycle.MutableLiveData
import com.paul.chef.data.*

class DefaultChefRepository(private val firebaseDataSource:ChefDataSource):ChefRepository {

    override suspend fun getMenuList(): Result<List<Menu>> {
        return firebaseDataSource.getMenuList()
    }

    override fun getLiveMenuList(): MutableLiveData<List<Menu>> {
        return firebaseDataSource.getLiveMenuList()
    }

    override suspend fun getUserByEmail(email: String): Result<User?> {
        return firebaseDataSource.getUserByEmail(email)
    }

    override fun getLiveUser(): MutableLiveData<User> {
        return firebaseDataSource.getLiveUser()
    }

    override suspend fun getChef(id: String): Result<Chef> {
        return firebaseDataSource.getChef(id)
    }

    override suspend fun getChefIdList(settingType: List<Int>): Result<List<String>> {
        return firebaseDataSource.getChefIdList(settingType)
    }

    override fun getLiveChef(id: String): MutableLiveData<Chef> {
        return firebaseDataSource.getLiveChef(id)
    }

    override suspend fun blockMenu(blockMenuList: List<String>, userId: String) {
        return firebaseDataSource.blockMenu(blockMenuList, userId)
    }

    override suspend fun blockReview(bloclkReviewList: List<String>, userId: String) {
        return firebaseDataSource.blockReview(bloclkReviewList,userId)
    }

    override suspend fun createChef(user: User): Result<String> {
        return firebaseDataSource.createChef(user)
    }

    override suspend fun updateOrderStatus(status: Int, orderId: String) {
        return firebaseDataSource.updateOrderStatus(status, orderId)
    }

    override suspend fun updateChefReview(
        chefId: String,
        newChefRating: Float,
        newChefRatingNumber: Int
    ) {
        return firebaseDataSource.updateChefReview(chefId, newChefRating, newChefRatingNumber)
    }

    override suspend fun updateMenuReview(
        menuId: String,
        newMenuRating: Float,
        newMenuRatingNumber: Int
    ) {
        return firebaseDataSource.updateMenuReview(menuId, newMenuRating, newMenuRatingNumber)
    }

    override suspend fun getMenu(id: String): Result<Menu> {
        return firebaseDataSource.getMenu(id)
    }

    override suspend fun setReview(menuId: String, review: Review) {
        return firebaseDataSource.setReview(menuId, review)
    }

    override suspend fun setRoom(
        userId: String,
        chefId: String,
        useName: String,
        chefName: String,
        userAvatar: String,
        chefAvatar: String
    ): Result<String> {
        return firebaseDataSource.setRoom(userId, chefId, useName, chefName, userAvatar, chefAvatar)
    }

    override suspend fun getRoom(userId: String, chefId: String): Result<String> {
        return firebaseDataSource.getRoom(userId, chefId)
    }

    override suspend fun setMenu(
        menuName: String,
        menuIntro: String,
        perPrice: Int,
        images: List<String>,
        discountList: List<Discount>,
        dishList: List<Dish>,
        tagList: List<String>,
        openBoolean: Boolean
    ) {
        return firebaseDataSource.setMenu(menuName, menuIntro, perPrice, images, discountList, dishList, tagList, openBoolean)
    }

    override suspend fun updateLikeList(newList: List<String>) {
        return firebaseDataSource.updateLikeList(newList)
    }

    override suspend fun getMenuReviewList(menuId: String): Result<List<Review>> {
        return firebaseDataSource.getMenuReviewList(menuId)
    }
}