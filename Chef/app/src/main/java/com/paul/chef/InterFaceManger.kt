package com.paul.chef

import com.paul.chef.data.ChefMenu


interface AddDiscount{
       fun add(people:Int, percentOff:Int)

       fun remove(position: Int, percentOff:Int)
   }


    interface MenuDetail{
        fun goDetail(menu:ChefMenu)
    }


