package com.paul.chef.ui.transaction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.paul.chef.OrderStatus
import com.paul.chef.R
import com.paul.chef.TransactionStatus
import com.paul.chef.databinding.FragmentOrderManageBinding
import com.paul.chef.databinding.FragmentTransactionBinding
import com.paul.chef.ui.orderManage.OrderAdapter

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    lateinit var transactionAdapter: TransactionAdapter

    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        //viewPager2
        transactionAdapter = TransactionAdapter(this)
        binding.transactionViewpager2.adapter = transactionAdapter

        TabLayoutMediator(binding.transactionTabs, binding.transactionViewpager2) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = TransactionStatus.PENDING.value
                }
                1 -> {
                    tab.text = TransactionStatus.PROCESSING.value
                }
                2 -> {
                    tab.text = TransactionStatus.COMPLETED.value
                }

            }

        }.attach()

        binding.transactionApplyBtn.setOnClickListener {

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}