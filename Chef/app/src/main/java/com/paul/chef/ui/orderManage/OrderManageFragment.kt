package com.paul.chef.ui.orderManage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.paul.chef.*
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.databinding.FragmentOrderManageBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.addressList.AddressListViewModel

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


        when(UserManger.readData("mode")){
            Mode.USER.index-> {
                binding.orderManageTitle.text = getString(R.string.enjoy_food)
            }
            Mode.CHEF.index-> {

                binding.orderManageTitle.text = getString(R.string.receive_order)
            }
        }

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