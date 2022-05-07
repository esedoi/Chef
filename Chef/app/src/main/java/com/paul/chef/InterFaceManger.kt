package com.paul.chef

import com.paul.chef.data.Address
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.Order


interface AddDiscount{
           fun add(people:Int, percentOff:Int)
           fun remove(position: Int, percentOff:Int)
       }

    interface ItemMenu{
        fun goDetail(menu:ChefMenu)
        fun like(menuId:String)
    }

    interface GoOrderDetail{
        fun goDetail(order: Order)
    }

    interface GoChatRoom{
        fun goChatRoom(roomId:String)
    }

    interface MenuEditImg{
        fun remove(position:Int)
    }

    interface AddressList{
        fun select(item: Address)
    }


