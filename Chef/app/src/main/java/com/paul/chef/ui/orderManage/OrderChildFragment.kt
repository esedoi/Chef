package com.paul.chef.ui.orderManage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.databinding.FragmentOrderChildBinding

class OrderChildFragment : Fragment(), GoOrderDetail {

    //binding
    private var _binding: FragmentOrderChildBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private lateinit var orderViewModel: OrderManageViewModel

    private lateinit var orderChildAdapter: OrderChildAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var status = -1


    companion object {

        fun newInstance(position: Int): OrderChildFragment {
            val fragment = OrderChildFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentOrderChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //viewModel
        orderViewModel = ViewModelProvider(this)[OrderManageViewModel::class.java]

        status = requireArguments().getInt("position")

        orderViewModel.hasData.observe(viewLifecycleOwner) {
            orderViewModel.getList(status)
        }

        orderViewModel.orderList.observe(viewLifecycleOwner) {
            val mode = UserManger.readData("mode", (activity as MainActivity))
            when {
                it.isEmpty() && mode == Mode.USER.index -> {
                    binding.chatUserEmptyImg.visibility = View.VISIBLE
                    binding.chatEmptyTxt.visibility = View.VISIBLE
                    binding.chatChefEmptyImg.visibility = View.GONE
                }
                it.isEmpty() && mode == Mode.CHEF.index -> {
                    binding.chatUserEmptyImg.visibility = View.GONE
                    binding.chatEmptyTxt.visibility = View.VISIBLE
                    binding.chatChefEmptyImg.visibility = View.VISIBLE
                }
                it.isNotEmpty() && mode == Mode.USER.index -> {
                    binding.chatUserEmptyImg.visibility = View.GONE
                    binding.chatEmptyTxt.visibility = View.GONE
                    binding.chatChefEmptyImg.visibility = View.GONE
                }
                it.isNotEmpty() && mode == Mode.CHEF.index -> {
                    binding.chatUserEmptyImg.visibility = View.GONE
                    binding.chatEmptyTxt.visibility = View.GONE
                    binding.chatChefEmptyImg.visibility = View.GONE
                }
            }
            orderChildAdapter.submitList(it)
            orderChildAdapter.notifyDataSetChanged()
        }

        val mode = UserManger.readData("mode", (activity as MainActivity))

        //menuList recycler
        orderChildAdapter = mode?.let { OrderChildAdapter(this, it) }!!
        layoutManager = LinearLayoutManager(this.context)
        binding.orderRecycler.layoutManager = layoutManager
        binding.orderRecycler.adapter = orderChildAdapter


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goDetail(order: Order) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalOrderDetailFragment(
                order
            )
        )
    }
}