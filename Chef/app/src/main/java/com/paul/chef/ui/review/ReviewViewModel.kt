package com.paul.chef.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.LoadApiStatus
import com.paul.chef.OrderStatus
import com.paul.chef.data.Order
import com.paul.chef.data.Review
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import java.util.*
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: ChefRepository) : ViewModel() {

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

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

            when (val result = repository.getChef(chefId, true)) {
                is Result.Success -> {
                    val chefRatingNumber = result.data.reviewNumber ?: 0
                    var chefRating = result.data.reviewRating ?: 0
                    chefRating = chefRating.toFloat()
                    newChefRatingNumber = chefRatingNumber + 1
                    newChefRating = ((chefRating * chefRatingNumber) + rating) / newChefRatingNumber
                    repository.updateChefReview(chefId, newChefRating, newChefRatingNumber)
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                Result.Loading -> {

                }
            }

            when (val result = repository.getMenu(menuId)) {
                is Result.Success -> {
                    val menuRatingNumber = result.data.reviewNumber ?: 0
                    val menuRating = (result.data.reviewRating ?: 0).toInt().toFloat()
                    newMenuRatingNumber = menuRatingNumber + 1
                    newMenuRating = ((menuRating * menuRatingNumber) + rating) / newMenuRatingNumber
                    repository.updateMenuReview(menuId, newMenuRating, newMenuRatingNumber)
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                Result.Loading -> {

                }
            }

            val date = Calendar.getInstance().timeInMillis
            val review = Review(rating, order.userId, order.userName, order.userAvatar, txt, date)
            repository.setReview(menuId, review)
        }
    }
}
