package com.paul.chef.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.MainViewModel
import com.paul.chef.data.source.ChefRepository
import com.paul.chef.ui.addressList.AddressListViewModel
import com.paul.chef.ui.book.BookViewModel
import com.paul.chef.ui.bookSetting.BookSettingViewModel
import com.paul.chef.ui.bottomSheetPicker.PickerViewModel
import com.paul.chef.ui.calendar.CalendarViewModel
import com.paul.chef.ui.calendarSetting.CalendarSettingViewModel
import com.paul.chef.ui.chatList.ChatListViewModel
import com.paul.chef.ui.chatRoom.ChatRoomViewModel
import com.paul.chef.ui.chef.ChefViewModel
import com.paul.chef.ui.chefEdit.ChefEditViewModel
import com.paul.chef.ui.datePicker.DatePickerViewModel
import com.paul.chef.ui.menu.MenuListViewModel
import com.paul.chef.ui.menuDetail.MenuDetailViewModel
import com.paul.chef.ui.menuEdit.MenuEditViewModel
import com.paul.chef.ui.orderDetail.OrderDetailViewModel
import com.paul.chef.ui.orderManage.OrderManageViewModel
import com.paul.chef.ui.review.ReviewViewModel
import com.paul.chef.ui.transaction.TransactionViewModel
import com.paul.chef.ui.userProfile.UserProfileViewModel

class ViewModelFactory constructor(
    private val repository: ChefRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(UserProfileViewModel::class.java) ->
                    UserProfileViewModel(repository)

                isAssignableFrom(ReviewViewModel::class.java) ->
                    ReviewViewModel(repository)

                isAssignableFrom(OrderDetailViewModel::class.java) ->
                    OrderDetailViewModel(repository)

                isAssignableFrom(MenuEditViewModel::class.java) ->
                    MenuEditViewModel(repository)

                isAssignableFrom(MenuDetailViewModel::class.java) ->
                    MenuDetailViewModel(repository)

                isAssignableFrom(MenuListViewModel::class.java) ->
                    MenuListViewModel(repository)

                isAssignableFrom(DatePickerViewModel::class.java) ->
                    DatePickerViewModel(repository)

                isAssignableFrom(ChefEditViewModel::class.java) ->
                    ChefEditViewModel(repository)

                isAssignableFrom(ChefViewModel::class.java) ->
                    ChefViewModel(repository)

                isAssignableFrom(ChatRoomViewModel::class.java) ->
                    ChatRoomViewModel(repository)

                isAssignableFrom(ChatListViewModel::class.java) ->
                    ChatListViewModel(repository)

                isAssignableFrom(CalendarSettingViewModel::class.java) ->
                    CalendarSettingViewModel(repository)

                isAssignableFrom(CalendarViewModel::class.java) ->
                    CalendarViewModel(repository)

                isAssignableFrom(PickerViewModel::class.java) ->
                    PickerViewModel(repository)

                isAssignableFrom(BookSettingViewModel::class.java) ->
                    BookSettingViewModel(repository)

                isAssignableFrom(BookViewModel::class.java) ->
                    BookViewModel(repository)

                isAssignableFrom(AddressListViewModel::class.java) ->
                    AddressListViewModel(repository)

                isAssignableFrom(OrderManageViewModel::class.java) ->
                    OrderManageViewModel(repository)

                isAssignableFrom(TransactionViewModel::class.java) ->
                    TransactionViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
