package com.paul.chef.ui.orderManage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paul.chef.R

class OrderManageFragment : Fragment() {

    companion object {
        fun newInstance() = OrderManageFragment()
    }

    private lateinit var viewModel: OrderManageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_manage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderManageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}