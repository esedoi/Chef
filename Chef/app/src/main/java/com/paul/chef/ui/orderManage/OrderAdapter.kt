package com.paul.chef.ui.orderManage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paul.chef.OrderStatus

class OrderAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return OrderStatus.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return OrderChildFragment.newInstance(position)
    }
}
