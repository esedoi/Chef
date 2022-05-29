package com.paul.chef.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.OrderStatus
import com.paul.chef.TransactionStatus
import com.paul.chef.databinding.FragmentTransactionBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.util.Util.getPrice

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionAdapter: TransactionAdapter

    private var pendingMoney = 0
    private var processingMoney = 0
    private var receivedMoney = 0
    private var idList = mutableListOf<String>()

    private val transactionViewModel by viewModels<TransactionViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // viewPager2
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

        transactionViewModel.orderList.observe(viewLifecycleOwner) {
            pendingMoney = 0
            for (i in it) {
                pendingMoney += i.chefReceive
                idList.add(i.id)
            }
            binding.transactionPenddingTxt.text = getPrice(pendingMoney)
        }

        transactionViewModel.transactionList.observe(viewLifecycleOwner) {
            receivedMoney = 0
            processingMoney = 0
            for (i in transactionViewModel.processingList) {
                processingMoney += i.chefReceive
            }
            for (i in transactionViewModel.receivedList) {
                receivedMoney += i.chefReceive
            }

            binding.transactionCompletedTxt.text = getPrice(receivedMoney)
            binding.transactionProcessingTxt.text = getPrice(processingMoney)
        }

        binding.transactionApplyBtn.setOnClickListener {
            transactionViewModel.applyMoney(pendingMoney)
            for (i in idList) {
                transactionViewModel.changeStatus(i, OrderStatus.APPLIED.index)
                if(idList.indexOf(i)==idList.lastIndex){
                    findNavController().navigate(MobileNavigationDirections.actionGlobalTransactionFragment())
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
