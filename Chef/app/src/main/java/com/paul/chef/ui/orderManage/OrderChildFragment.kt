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
import com.paul.chef.data.ChefMenu
import com.paul.chef.data.Order
import com.paul.chef.databinding.FragmentOrderChildBinding
import com.paul.chef.ui.menu.MenuListAdapter

class OrderChildFragment: Fragment(), GoOrderDetail{

    //binding
    private var _binding: FragmentOrderChildBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private lateinit var orderViewModel: OrderManageViewModel

    private lateinit var orderChildAdapter: OrderChildAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private  var status = -1


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
        Log.d("orderchildfragment", "status=$status")

        orderViewModel.hasData.observe(viewLifecycleOwner){
                    orderViewModel.getList(status)
        }

        orderViewModel.orderList.observe(viewLifecycleOwner){
            Log.d("orderfragment", "it = $it")
            orderChildAdapter.submitList(it)
        }

        //menuList recycler
        orderChildAdapter = OrderChildAdapter(this)
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
        Log.d("orderchildfragment", "order=$order")
        findNavController().navigate(MobileNavigationDirections.actionGlobalOrderDetailFragment(order))
    }
}