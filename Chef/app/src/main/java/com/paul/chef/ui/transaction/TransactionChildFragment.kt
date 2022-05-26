package com.paul.chef.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.data.Order
import com.paul.chef.data.Transaction
import com.paul.chef.databinding.FragmentTransactionChildBinding
import com.paul.chef.ext.getVmFactory

class TransactionChildFragment : Fragment() {

    private var _binding: FragmentTransactionChildBinding? = null
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionViewModel by viewModels(
        { requireParentFragment() }
    ) { getVmFactory() }

    private lateinit var transactionUnpaidAdapter: TransactionUnpaidAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var transactionChildAdapter: TransactionChildAdapter

    private var status = -1

    companion object {

        fun newInstance(position: Int): TransactionChildFragment {
            val fragment = TransactionChildFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args

            return fragment
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View {
        _binding = FragmentTransactionChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        status = requireArguments().getInt("position")

        when (status) {
            0 -> {
                transactionUnpaidAdapter = TransactionUnpaidAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionUnpaidAdapter
                transactionViewModel.liveOrderList.observe(viewLifecycleOwner) {
                    transactionViewModel.getUnpaidList(it)
                }
                transactionViewModel.orderList.observe(viewLifecycleOwner) {
                    orderListEmptyHandle(it)
                    transactionUnpaidAdapter.submitList(it)
                    transactionUnpaidAdapter.notifyDataSetChanged()
                }
            }
            1 -> {
                transactionChildAdapter = TransactionChildAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionChildAdapter
                transactionViewModel.getList(status)
                transactionViewModel.transactionList.observe(viewLifecycleOwner) {
                    transactionListEmptyHandle(transactionViewModel.processingList)
                    transactionChildAdapter.submitList(transactionViewModel.processingList)
                    transactionChildAdapter.notifyDataSetChanged()
                }
            }
            2 -> {
                transactionChildAdapter = TransactionChildAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionChildAdapter
                transactionViewModel.getList(status)
                transactionViewModel.transactionList.observe(viewLifecycleOwner) {
                    transactionListEmptyHandle(transactionViewModel.receivedList)
                    transactionChildAdapter.submitList(transactionViewModel.receivedList)
                    transactionChildAdapter.notifyDataSetChanged()
                }
            }
        }

        return root
    }

    private fun transactionListEmptyHandle(transactionList: MutableList<Transaction>) {
        if (transactionList.isEmpty()) {
            binding.chatChefEmptyImg.visibility = View.VISIBLE
            binding.chatEmptyTxt.visibility = View.VISIBLE
        } else {
            binding.chatChefEmptyImg.visibility = View.GONE
            binding.chatEmptyTxt.visibility = View.GONE
        }
    }

    private fun orderListEmptyHandle(it: List<Order>) {
        if (it.isEmpty()) {
            binding.chatChefEmptyImg.visibility = View.VISIBLE
            binding.chatEmptyTxt.visibility = View.VISIBLE
        } else {
            binding.chatChefEmptyImg.visibility = View.GONE
            binding.chatEmptyTxt.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
