package com.paul.chef.ui.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.OrderStatus
import com.paul.chef.data.Order
import com.paul.chef.data.Review
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import java.util.*
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: ChefRepository) : ViewModel() {

    fun rating(txt: String, rating: Float, order: Order) {
        val orderId = order.id
        val status = OrderStatus.SCORED.index
        val menuId = order.menuId
        val chefId = order.chefId
        var newChefRating: Float
        var newChefRatingNumber: Int
        var newMenuRating: Float
        var newMenuRatingNumber: Int

        viewModelScope.launch {
            repository.updateOrderStatus(status, orderId)

            when (val chef = repository.getChef(chefId)) {
                is Result.Success -> {
                    val chefRatingNumber = chef.data.reviewNumber ?: 0
                    var chefRating = chef.data.reviewRating ?: 0
                    chefRating = chefRating.toFloat()
                    newChefRatingNumber = chefRatingNumber + 1
                    newChefRating = ((chefRating * chefRatingNumber) + rating) / newChefRatingNumber
                    repository.updateChefReview(chefId, newChefRating, newChefRatingNumber)
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }

            when (val menu = repository.getMenu(menuId)) {
                is Result.Success -> {
                    val menuRatingNumber = menu.data.reviewNumber ?: 0
                    val menuRating = (menu.data.reviewRating ?: 0).toInt().toFloat()
                    newMenuRatingNumber = menuRatingNumber + 1
                    newMenuRating = ((menuRating * menuRatingNumber) + rating) / newMenuRatingNumber
                    repository.updateMenuReview(menuId, newMenuRating, newMenuRatingNumber)
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
            }

            val date = Calendar.getInstance().timeInMillis
            val review = Review(rating, order.userId, order.userName, order.userAvatar, txt, date)
            repository.setReview(menuId, review)
        }
    }
}
