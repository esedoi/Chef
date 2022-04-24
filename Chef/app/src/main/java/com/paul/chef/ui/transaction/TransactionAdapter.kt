package com.paul.chef.ui.transaction

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paul.chef.OrderStatus
import com.paul.chef.TransactionStatus
import com.paul.chef.ui.orderManage.OrderChildFragment

class TransactionAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return TransactionChildFragment.newInstance(position)
    }
}