package com.paul.chef.ui.chef

import androidx.lifecycle.*
import com.paul.chef.LoadApiStatus
import com.paul.chef.data.Chef
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class ChefViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _chefInfo = MutableLiveData<Chef>()
    val chefInfo: LiveData<Chef>
        get() = _chefInfo

    private val dataList = mutableListOf<Review>()

    private var _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

    private val menuIdList = mutableListOf<String>()

    private var _liveMenu = MutableLiveData<List<Menu>>()
    val liveMenu: LiveData<List<Menu>>
        get() = _liveMenu

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getChef(chefId: String) {
        _chefInfo = repository.getLiveChef(chefId)

        menuIdList.clear()

        viewModelScope.launch {
            when (val result = repository.getChefMenuList(chefId)) {
                is Result.Success -> {
                    _liveMenu.value = result.data ?: return@launch
                    for (menu in result.data) {
                        menuIdList.add(menu.id)
                        if (result.data.indexOf(menu) == result.data.lastIndex) {
                            getChefReview(menuIdList)
                        }
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

    private fun getChefReview(menuIdList: List<String>) {
        dataList.clear()
        viewModelScope.launch {
            for (id in menuIdList) {
                when (val result = repository.getMenuReviewList(id)) {
                    is Result.Success -> {
                        for (review in result.data) {
                            dataList.add(review)
                        }
                        if (menuIdList.indexOf(id) == menuIdList.lastIndex) {
                            _reviewList.value = dataList
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
    }
}
