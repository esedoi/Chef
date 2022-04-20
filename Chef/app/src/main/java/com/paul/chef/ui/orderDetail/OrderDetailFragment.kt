package com.paul.chef.ui.orderDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.paul.chef.ChefManger
import com.paul.chef.OrderStatus
import com.paul.chef.R
import com.paul.chef.databinding.FragmentBookBinding
import com.paul.chef.databinding.FragmentOrderDetailBinding
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.databinding.ItemDisplayDishBinding
import com.paul.chef.ui.book.BookFragmentArgs

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private var _itemDisplayBinding: ItemDisplayDishBinding? = null
    private val itemDisplayBinding get() = _itemDisplayBinding!!

    private val displayList = mutableListOf<ItemDisplayDishBinding>()


    private lateinit var viewModel: OrderDetailViewModel

    private val arg: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(OrderDetailViewModel::class.java)
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val order = arg.order


        binding.apply {
            orderDetailType.text = order.type.toString()
            orderDetailStatus.text = order.status.toString()
            orderDetailDate.text = order.date.toString()
            orderDetailName.text = order.userName
            orderDetailMenuName.text = order.menuName
            orderDetailNote.text = order.note
            orderDetailPeople.text = order.people.toString()
            orderDetailOrginalPrice.text = order.originalPrice.toString()
            orderDetailDiscount.text = order.discount.toString()
            orderDetailFee.text = ChefManger().fee.toString()
            orderDetailTotal.text = order.total.toString()

            orderDetailAccept.setOnClickListener {
                viewModel.changeStatus(order.id,OrderStatus.UPCOMING.index)

            }
            orderDetailRefuse.setOnClickListener {
                viewModel.changeStatus(order.id,OrderStatus.CANCELLED.index)
            }
        }

        var defaultType = -1
        val and = "and"

        for (i in order.selectedDish){

            _itemDisplayBinding =
                ItemDisplayDishBinding.inflate(LayoutInflater.from(context), container, false)
            if (i.typeNumber != defaultType) {
                //setType
                itemDisplayBinding.displayTitle.text = i.type
                defaultType = i.typeNumber
                binding.orderDetailLinear.addView(itemDisplayBinding.root)
                displayList.add(itemDisplayBinding)
            } else {
                //+and
                val andText = TextView(this.context)
                andText.text = and
                andText.gravity = Gravity.CENTER
                andText.setTextAppearance(android.R.style.TextAppearance_Material_Body2)
                displayList[defaultType].displayRG.addView(andText)
            }
            // +textview
            val nameText = TextView(this.context)

            val name= if(i.extraPrice!=0){
                "${i.name}    <font color = \"#03A9F4\">(+NT$ ${i.extraPrice})</font>"
            }else{
                "${i.name}"
            }


            nameText.text = Html.fromHtml(name)
            nameText.gravity = Gravity.CENTER
            displayList[defaultType].displayRG.addView(nameText)
            displayList[defaultType].displayRG.gravity = Gravity.CENTER

        }


        return root
    }



}