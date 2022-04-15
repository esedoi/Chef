package com.paul.chef.ui.orderSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.paul.chef.data.MenuStatus
import com.paul.chef.databinding.FragmentCalendarSettingBinding
import com.paul.chef.databinding.FragmentOrderSettingBinding
import com.paul.chef.ui.calendarSetting.CalendarSettingViewModel

class OrderSetting: Fragment() {

    private var _binding: FragmentOrderSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val orderSettingViewModel =
            ViewModelProvider(this).get(OrderSettingViewModel::class.java)



        binding.save.setOnClickListener {
            orderSettingViewModel.setting()
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
