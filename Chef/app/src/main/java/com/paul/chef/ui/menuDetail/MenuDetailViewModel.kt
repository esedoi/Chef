package com.paul.chef.ui.menuDetail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.paul.chef.UserManger
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.data.User
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class MenuDetailViewModel(private val repository: ChefRepository) : ViewModel() {


    var liveUser = MutableLiveData<User>()

    private var _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

    private var _bookSettingType = MutableLiveData<Int?>()
    val bookSettingType: LiveData<Int?>
        get() = _bookSettingType

    val userId = UserManger.user?.userId!!



    init {

        liveUser = repository.getLiveUser()

    }

    fun checkOpen(chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getChef(chefId)) {
                is Result.Success -> {
                    _bookSettingType.value = if (result.data.bookSetting == null) {
                        null
                    } else {
                        result.data.bookSetting.type
                    }
                }
            }
        }
    }


    fun getReview(menuId: String) {

        viewModelScope.launch {
            when (val result = repository.getMenuReviewList(menuId)) {
                is Result.Success -> {
                    _reviewList.value = result.data!!
                }
            }
        }
    }

    fun updateLikeList(newList: List<String>) {

        viewModelScope.launch {
            repository.updateLikeList(newList)
        }

    }

}