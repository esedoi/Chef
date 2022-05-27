package com.paul.chef.ui.menuEdit

import androidx.lifecycle.*
import com.paul.chef.BookSettingType
import com.paul.chef.LoadApiStatus
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import kotlinx.coroutines.launch

class MenuEditViewModel(private val repository: ChefRepository) : ViewModel() {

    val chefId = UserManger.chef?.id!!

    private var _openBoolean = MutableLiveData<Boolean>()
    val openBoolean: LiveData<Boolean>
        get() = _openBoolean

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {

        viewModelScope.launch {
            when (val result = repository.getChef(chefId)) {
                is Result.Success -> {
                    _openBoolean.value =
                        result.data.bookSetting != null && result.data.bookSetting.type != BookSettingType.RefuseAll.index
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

    fun createMenu(
        menuName: String,
        menuIntro: String,
        perPrice: Int,
        images: List<String>,
        discountList: List<Discount>,
        dishList: List<Dish>,
        tagList: List<String>,
        openBoolean: Boolean
    ) {
        viewModelScope.launch {
            repository.setMenu(
                menuName,
                menuIntro,
                perPrice,
                images,
                discountList,
                dishList,
                tagList,
                openBoolean
            )
        }
    }
}
