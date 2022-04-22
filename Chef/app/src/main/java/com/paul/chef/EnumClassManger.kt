package com.paul.chef

enum class BookSettingType(val index: Int) {
    AcceptAll(0), OnlyUserSpace(1), OnlyChefSpace(2), RefuseAll(3)
}

enum class BookType(val index: Int, val userTxt: String, val chefTxt:String) {
    UserSpace(0, "預訂廚師來我的空間料理", "前往客人空間料理"), ChefSpace(1, "前往廚師空間用餐", "來我的廚房用餐")
}

enum class CalendarType(val index: Int) {
    AllDayClose(0), AllDayOpen(1)
}

enum class DateStatus(val index: Int) {
    CLOSE(0), OPEN(1)
}

enum class Mode(val index: Int) {
    LOGOUT(0), USER(1), CHEF(2)
}

enum class OrderStatus(val index: Int, val value: String) {
    PENDING(0, "待處理"),
    UPCOMING(1, "即將到來"),
    COMPLETED(2, "已完成"),
    CANCELLED(3, "已取消")
}

enum class PickerType(val index: Int, val value: String) {
    SET_CAPACITY(0, "setCapacity"),
    SET_SESSION_CAPACITY(1, "setSessionCapacity"),
    SET_SESSION_TIME(2, "setSessionTime"),
    SET_START_TIME(3, "setStartTime"),
    SET_END_TIME(4, "setEndTime"),
    PICK_CAPACITY(5, "pickCapacity"),
    PICK_SESSION_CAPACITY(6, "pickSessionCapacity"),
    PICK_SESSION_TIME(7, "pickSessionTime"),
    PICK_TIME(8, "pickTime")
}

enum class Like(val index: Int) {
    ADD(0), REMOVE(1)
}

