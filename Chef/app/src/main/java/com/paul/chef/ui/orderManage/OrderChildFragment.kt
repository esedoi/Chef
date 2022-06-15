package com.paul.chef.ui.orderManage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Order
import com.paul.chef.databinding.FragmentOrderChildBinding
import com.paul.chef.ext.getVmFactory
import timber.log.Timber

class OrderChildFragment : Fragment(), GoOrderDetail {

    private var _binding: FragmentOrderChildBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel by viewModels<OrderManageViewModel> { getVmFactory() }

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View {
        _binding = FragmentOrderChildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        status = requireArguments().getInt("position")

        orderViewModel.liveOrderList.observe(viewLifecycleOwner) {
            orderViewModel.sortOrder(it)
        }

        orderViewModel.hasData.observe(viewLifecycleOwner) {
            orderViewModel.getList(status)
        }

        orderViewModel.orderList.observe(viewLifecycleOwner) {
            Timber.d("it.size = ${it.size}")
            setWhetherEmpty(it)
            orderChildAdapter.submitList(it)
            orderChildAdapter.notifyDataSetChanged()
        }

        val mode = UserManger.readData("mode")

        orderChildAdapter = OrderChildAdapter(this, mode)
        layoutManager = LinearLayoutManager(this.context)
        binding.orderRecycler.layoutManager = layoutManager
        binding.orderRecycler.adapter = orderChildAdapter

        return root
    }

    private fun setWhetherEmpty(it: List<Order>) {
        val mode = UserManger.readData("mode")
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
