package com.paul.chef.ui.transaction

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    var penddingMoney = 0
    var processingMoney = 0
    var receivedMoney = 0
    var idList = mutableListOf<String>()

    val transactionViewModel:TransactionViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root



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

        transactionViewModel.orderList.observe(viewLifecycleOwner){
            penddingMoney = 0
            for (i in it){
                penddingMoney+=i.chefReceive
                idList.add(i.id)
            }
            binding.transactionPenddingTxt.text = penddingMoney.toString()
        }

        transactionViewModel.transactionList.observe(viewLifecycleOwner){
            Log.d("transactionfragment", "transactionList, it= $it")
            Log.d("transactionfragment", "transactionViewModel.processingList= ${transactionViewModel.processingList}")
            Log.d("transactionfragment", "transactionViewModel.receivedList= ${transactionViewModel.receivedList}")

            receivedMoney = 0
            processingMoney = 0
            for(i in transactionViewModel.processingList){
                processingMoney += i.chefReceive
            }
            for(i in transactionViewModel.receivedList){
                receivedMoney += i.chefReceive
            }
            binding.transactionCompletedTxt.text = receivedMoney.toString()
            binding.transactionProcessingTxt.text = processingMoney.toString()
        }



        binding.transactionApplyBtn.setOnClickListener {
            Log.d("transactionfragment", "processingMoney = $penddingMoney")
            transactionViewModel.applyMoney(penddingMoney)
            for (i in idList){
                transactionViewModel.changeStatus(i, OrderStatus.APPLIED.index)
            }
            super.onCreateView(inflater, container, savedInstanceState)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}