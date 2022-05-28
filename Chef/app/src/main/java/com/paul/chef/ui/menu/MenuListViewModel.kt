package com.paul.chef.ui.menu

import androidx.lifecycle.*
import com.paul.chef.BookSettingType
import com.paul.chef.BookType
import com.paul.chef.LoadApiStatus
import com.paul.chef.data.Menu
import com.paul.chef.data.User
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class MenuListViewModel(private val repository: ChefRepository) : ViewModel() {

    private var _menuList = MutableLiveData<List<Menu>>()
    val menuList: LiveData<List<Menu>>
        get() = _menuList

    private var _likeIdList = MutableLiveData<List<String>>()
    val likeIdList: LiveData<List<String>>
        get() = _likeIdList

    private var _likeList = MutableLiveData<List<Menu>>()
    val likeList: LiveData<List<Menu>>
        get() = _likeList

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    var liveUser = MutableLiveData<User>()

    private val chefList = mutableListOf<String>()

    init {
        liveUser = repository.getLiveUser()
        _menuList = repository.getLiveMenuList()
    }

    fun filterLikeIdList(user: User) {
        if (user.likeList != null) {
            _likeIdList.value = user.likeList ?: return
        } else {
            _likeIdList.value = emptyList()
        }
    }

    fun getLikeList(newList: List<String>, menuList: List<Menu>) {
        if (newList.isEmpty()) {
            _likeList.value = emptyList()
        } else {
            val likeMenuList = menuList.filter {
                newList.contains(it.id)
            }
            _likeList.value = likeMenuList
        }
    }

    fun getChefId(bookType: Int) {
        val settingType = mutableListOf<Int>()
        settingType.add(BookSettingType.AcceptAll.index)
        if (bookType == BookType.UserSpace.index) {
            settingType.add(BookSettingType.OnlyUserSpace.index)
        } else {
            settingType.add(BookSettingType.OnlyChefSpace.index)
        }
        viewModelScope.launch {
            when (val result = repository.getChefIdList(settingType)) {
                is Result.Success -> {
                    getFilterMenuList(result.data)
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

    fun getFilterMenuList(chefIdList: List<String>) {
        viewModelScope.launch {
            when (val result = repository.getMenuList()) {
                is Result.Success -> {
                    val filterList = result.data.filter {
                        chefIdList.contains(it.chefId)
                    }
                    _menuList.value = filterList
                    chefList.clear()
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
