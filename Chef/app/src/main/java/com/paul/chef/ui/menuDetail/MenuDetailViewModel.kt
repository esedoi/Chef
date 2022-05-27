package com.paul.chef.ui.menuDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paul.chef.LoadApiStatus
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

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


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
        }
    }

    fun getReview(menuId: String) {
        viewModelScope.launch {
            when (val result = repository.getMenuReviewList(menuId)) {
                is Result.Success -> {
                    _reviewList.value = result.data?:return@launch
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
        }
    }

    fun updateLikeList(newList: List<String>) {
        viewModelScope.launch {
            repository.updateLikeList(newList)
        }
    }
}
