package com.paul.chef.ui.orderManage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.paul.chef.OrderStatus
import com.paul.chef.R
import com.paul.chef.UserManger
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.databinding.FragmentOrderManageBinding

class OrderManageFragment : Fragment() {




    private var _binding: FragmentOrderManageBinding? = null
    private val binding get() = _binding!!

    lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderManageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //viewPager2
        orderAdapter = OrderAdapter(this)
        binding.viewpager2.adapter = orderAdapter

        TabLayoutMediator(binding.tabs, binding.viewpager2) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = OrderStatus.PENDING.value
                }
                1 -> {
                    tab.text = OrderStatus.UPCOMING.value
                }
                2 -> {
                    tab.text = OrderStatus.COMPLETED.value
                }
                3 -> {
                    tab.text = OrderStatus.CANCELLED.value
                }
            }

        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}