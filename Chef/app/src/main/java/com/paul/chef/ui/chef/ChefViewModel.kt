package com.paul.chef.ui.chef

import androidx.lifecycle.*
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

    fun getChef(chefId: String) {
        _chefInfo = repository.getLiveChef(chefId)

        menuIdList.clear()

        viewModelScope.launch {
            when (val chefMenuList = repository.getChefMenuList(chefId)) {
                is Result.Success -> {
                    _liveMenu.value = chefMenuList.data!!
                    for (menu in chefMenuList.data) {
                        menuIdList.add(menu.id)
                        if (chefMenuList.data.indexOf(menu) == chefMenuList.data.lastIndex) {
                            getChefReview(menuIdList)
                        }
                    }
                }
                is Result.Error -> TODO()
                is Result.Fail -> TODO()
                Result.Loading -> TODO()
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
                    is Result.Error -> TODO()
                    is Result.Fail -> TODO()
                    Result.Loading -> TODO()
                }
            }
        }
    }
}
