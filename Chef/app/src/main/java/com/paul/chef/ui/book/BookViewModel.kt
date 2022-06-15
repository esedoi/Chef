package com.paul.chef.ui.book

import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.ChefManger
import com.paul.chef.LoadApiStatus
import com.paul.chef.UserManger
import com.paul.chef.data.*
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.data.source.Result
import com.paul.chef.util.ConstValue.DISCOUNT_PER_PRICE
import com.paul.chef.util.ConstValue.IS_DISCOUNT
import com.paul.chef.util.ConstValue.ORDER
import com.paul.chef.util.ConstValue.ORIGINAL_PRICE
import com.paul.chef.util.ConstValue.TOTAL
import com.paul.chef.util.ConstValue.USER_FEE
import com.paul.chef.util.ConstValue.USER_PAY
import java.util.*
import kotlinx.coroutines.launch

class BookViewModel(private val repository: ChefRepository) : ViewModel() {


    private var _chefSpaceAddress = MutableLiveData<Address>()
    val chefSpaceAddress: LiveData<Address>
        get() = _chefSpaceAddress

    private var _bookDone = MutableLiveData<Boolean>()
    val bookDone: LiveData<Boolean>
        get() = _bookDone

    private var userPay = -1
    private var chefReceive = -1

    private var _priceResult = MutableLiveData<Map<String, Int>>()
    val priceResult: LiveData<Map<String, Int>>
        get() = _priceResult

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getAddress(chefId: String) {
        viewModelScope.launch {
            when (val result = repository.getChef(chefId, true)) {
                is Result.Success -> {
                    _chefSpaceAddress.value = result.data.bookSetting?.chefSpace?.address
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

    fun orderPrice(menu: Menu, people: Int) {
        val originalPrice = menu.perPrice * people
        var total = originalPrice
        var isDiscount = 0
        val userFee: Int = UserManger().userFee
        val chefFee = ChefManger().chefFee

        for (i in menu.discount) {
            if (people >= i.people) {
                val d: Double = (i.percentOff.toDouble() / 100)
                total = (originalPrice * (1 - d)).toInt()
                isDiscount = 1
            }
        }
        val discountPerPrice = total / people
        userPay = total + userFee
        chefReceive = total - chefFee
        _priceResult.value = mapOf(
            DISCOUNT_PER_PRICE to discountPerPrice,
            ORIGINAL_PRICE to originalPrice,
            TOTAL to total,
            USER_PAY to userPay,
            USER_FEE to userFee,
            IS_DISCOUNT to isDiscount

        )
    }

    fun book(
        menu: Menu,
        type: Int,
        address: Address,
        datePicker: Long,
        time: String,
        note: String,
        people: Int,
        selectedDish: List<Dish>,
    ) {
        val db = FirebaseFirestore.getInstance()
        val orderId = db.collection(ORDER).document().id
        val userId = UserManger.user?.userId ?: return
        val userName = UserManger.user?.profileInfo?.name ?: return
        val chefName = menu.chefName
        val userPic = UserManger.user?.profileInfo?.avatar ?: "nullPic"
        val chefPic = menu.chefAvatar
        val menuName = menu.menuName
        val chefId = menu.chefId
        val orderTime = Calendar.getInstance().timeInMillis
        val menuId = menu.id
        val status = 0
        val originalPrice = menu.perPrice * people
        var total = originalPrice

        for (i in menu.discount) {
            if (people >= i.people) {
                val d: Double = (i.percentOff.toDouble() / 100)
                total = (originalPrice * (1 - d)).toInt()
            }
        }

        val discount = originalPrice - total

        val order = Order(
            orderId,
            userId,
            chefId,
            userName,
            chefName,
            userPic,
            chefPic,
            menuName,
            type,
            address,
            orderTime,
            datePicker,
            time,
            note,
            people,
            menuId,
            selectedDish,
            status,
            originalPrice,
            discount,
            userPay,
            chefReceive
        )

        viewModelScope.launch {
            when (val result = repository.setOrder(order)) {
                is Result.Success -> {
                    _bookDone.value = result.data ?: return@launch
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
