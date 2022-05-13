package com.paul.chef.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.databinding.FragmentTransactionChildBinding
import com.paul.chef.ui.orderManage.OrderChildFragment

class TransactionChildFragment: Fragment() {

    //binding
    private var _binding: FragmentTransactionChildBinding? = null
    private val binding get() = _binding!!
    //viewModel
//    private lateinit var transactionViewModel: TransactionViewModel
    val transactionViewModel:TransactionViewModel  by viewModels({requireParentFragment()})

    private lateinit var transactionUnpaidAdapter: TransactionUnpaidAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var transactionChildAdapter: TransactionChildAdapter


    private  var status = -1


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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentTransactionChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //viewModel
//        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]

        status = requireArguments().getInt("position")
        Log.d("orderchildfragment", "status=$status")

        when(status){
            0->{
                transactionUnpaidAdapter = TransactionUnpaidAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionUnpaidAdapter
                transactionViewModel.getList(status)
                transactionViewModel.orderList.observe(viewLifecycleOwner){
                    Log.d("transaction_child_fragment", "orderList_observe, it = $it")
                    if(it.isEmpty()){
                        binding.chatChefEmptyImg.visibility = View.VISIBLE
                        binding.chatEmptyTxt.visibility = View.VISIBLE
                    }else{
                        binding.chatChefEmptyImg.visibility = View.GONE
                        binding.chatEmptyTxt.visibility = View.GONE
                    }
                    transactionUnpaidAdapter.submitList(it)
                    transactionUnpaidAdapter.notifyDataSetChanged()
                }
            }
            1->{
                transactionChildAdapter = TransactionChildAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionChildAdapter
                transactionViewModel.getList(status)
                transactionViewModel.transactionList.observe(viewLifecycleOwner){
                    Log.d("transaction_child_fragment", " transactionList_1_observe , it = $it")
                    if(it.isEmpty()){
                        binding.chatChefEmptyImg.visibility = View.VISIBLE
                        binding.chatEmptyTxt.visibility = View.VISIBLE
                    }else{
                        binding.chatChefEmptyImg.visibility = View.GONE
                        binding.chatEmptyTxt.visibility = View.GONE
                    }
                    transactionChildAdapter.submitList( transactionViewModel.processingList)
                    transactionChildAdapter.notifyDataSetChanged()
                }
            }
            2->{
                transactionChildAdapter = TransactionChildAdapter()
                layoutManager = LinearLayoutManager(this.context)
                binding.transactionRecycler.layoutManager = layoutManager
                binding.transactionRecycler.adapter = transactionChildAdapter
                transactionViewModel.getList(status)
                transactionViewModel.transactionList.observe(viewLifecycleOwner){
                    Log.d("transaction_child_fragment", " transactionList_2_observe , it = $it")
                    if(it.isEmpty()){
                        binding.chatChefEmptyImg.visibility = View.VISIBLE
                        binding.chatEmptyTxt.visibility = View.VISIBLE
                    }else{
                        binding.chatChefEmptyImg.visibility = View.GONE
                        binding.chatEmptyTxt.visibility = View.GONE
                    }

                    transactionChildAdapter.submitList(transactionViewModel.receivedList)
                    transactionChildAdapter.notifyDataSetChanged()
                }

            }

        }



//        transactionViewModel.orderList.observe(viewLifecycleOwner){
//            Log.d("transactionchildfragment", "it = $it")
//            transactionUnpaidAdapter.submitList(it)
//        }
//
//        transactionViewModel.transactionList.observe(viewLifecycleOwner){
//            Log.d("transactionchildfragment", "it = $it")
//            transactionChildAdapter.submitList(it)
//        }







        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}