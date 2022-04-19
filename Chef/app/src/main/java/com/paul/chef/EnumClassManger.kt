package com.paul.chef

enum class BookSettingType(val index:Int){
    AcceptAll(0),OnlyUserSpace(1),OnlyChefSpace(2) ,RefuseAll(3)
}

enum class BookType(val index:Int){
    UserSpace(0), ChefSpace(1)
}

enum class CalendarType(val index:Int){
    AllDayClose(0),AllDayOpen(1)
}

enum class DateStatus(val index:Int){
    CLOSE(0),OPEN(1)
}

enum class Mode(val index:Int){
    LOGOUT(0),USER(1),CHEF(2)
}