package com.paul.chef.ui.orderDetail

import android.graphics.Typeface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.*
import com.paul.chef.databinding.FragmentBookBinding
import com.paul.chef.databinding.FragmentOrderDetailBinding
import com.paul.chef.databinding.FragmentUserProfileBinding
import com.paul.chef.databinding.ItemDisplayDishBinding
import com.paul.chef.ui.book.BookFragmentArgs
import java.time.LocalDate

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
        var roomId = ""


        val mode = UserManger.readData("mode", (activity as MainActivity))



        binding.apply {

            when(mode){
                Mode.CHEF.index->{
                    orderDetailType.text = when(order.type){
                        BookType.ChefSpace.index-> BookType.ChefSpace.chefTxt
                        BookType.UserSpace.index-> BookType.UserSpace.chefTxt
                        else->"something went wrong"
                    }
                    orderDetailName.text = order.userName
                    orderDetailPaymentText.text = "你的收款"
                    orderDetailTotal.text = order.chefReceive.toString()
                    if(order.status==OrderStatus.PENDING.index){
                        orderDetailAcceptBtn.visibility = View.VISIBLE
                    }else{
                        orderDetailAcceptBtn.visibility = View.GONE
                    }

                    orderDetailAcceptBtn.setOnClickListener {
                        viewModel.changeStatus(order.id,OrderStatus.UPCOMING.index)

                    }
                    orderDetailCancelBtn.text = when(order.status){
                        OrderStatus.PENDING.index ->"拒絕此訂單"
                        else->"取消訂單"
                    }
                    orderDetailCancelBtn.setOnClickListener {
                        viewModel.changeStatus(order.id,OrderStatus.CANCELLED.index)
                    }
                }
                Mode.USER.index->{
                    orderDetailType.text = when(order.type){
                        BookType.ChefSpace.index-> BookType.ChefSpace.userTxt
                        BookType.UserSpace.index-> BookType.UserSpace.userTxt
                        else->"something went wrong"
                    }
                    orderDetailName.text = order.chefName
                    orderDetailPaymentText.text = "你的付款"
                    orderDetailTotal.text = order.userPay.toString()
                    orderDetailAcceptBtn.visibility = View.GONE
                    orderDetailCancelBtn.text = "取消訂單"

                    if(order.status==OrderStatus.COMPLETED.index){
                        orderDetailRatingBar.visibility = View.VISIBLE
                        orderDetailReviewTxt.visibility = View.VISIBLE
                        orderDetailRatingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
                            findNavController().navigate(MobileNavigationDirections.actionGlobalReviewFragment(fl.toInt(),order))
                        }
                    }else{
                        orderDetailRatingBar.visibility = View.GONE
                        orderDetailReviewTxt.visibility = View.GONE
                    }

                }
            }

            orderDetailStatus.text = when(order.status){
                OrderStatus.PENDING.index->OrderStatus.PENDING.value
                OrderStatus.UPCOMING.index->OrderStatus.UPCOMING.value
                OrderStatus.COMPLETED.index->OrderStatus.COMPLETED.value
                OrderStatus.CANCELLED.index->OrderStatus.CANCELLED.value
                else->"something went wrong"
            }
            orderDetailDate.text = LocalDate.ofEpochDay(order.date).toString()

            orderDetailMenuName.text = order.menuName
            orderDetailNote.text = order.note
            orderDetailPeople.text = order.people.toString()+" 人"
            orderDetailOrginalPrice.text = order.originalPrice.toString()
            orderDetailDiscount.text = order.discount.toString()
            orderDetailFee.text = ChefManger().chefFee.toString()

            when(order.status){
                 OrderStatus.PENDING.index or OrderStatus.UPCOMING.index ->{
                    orderDetailCancelBtn.isClickable =true
                     orderDetailCancelBtn.setOnClickListener {
                         viewModel.changeStatus(order.id,OrderStatus.CANCELLED.index)
                     }
                }
                else->{
                    orderDetailCancelBtn.isClickable = false
                }
            }

            orderDetailSendBtn.setOnClickListener {
                if (roomId!=""){
                    findNavController().navigate(MobileNavigationDirections.actionGlobalChatRoomFragment(roomId))
                }
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
                andText.setTextColor(resources.getColor(R.color.teal_200))
                andText.setTypeface(null, Typeface.BOLD_ITALIC)
                displayList[defaultType].displayRG.addView(andText)
            }
            // +textview
            val nameText = TextView(this.context)
            nameText.setTextAppearance(android.R.style.TextAppearance_Material_Medium)
            val name= if(i.extraPrice!=0){
                "${i.name}    <I><font color = \"#03DAC5\">(+NT$ ${i.extraPrice})</font></I>"
            }else{
                "${i.name}"
            }
            nameText.text = Html.fromHtml(name)
            nameText.gravity = Gravity.CENTER
            displayList[defaultType].displayRG.addView(nameText)
            displayList[defaultType].displayRG.gravity = Gravity.CENTER

        }

        viewModel.getRoomId(order.userId, order.chefId)

        viewModel.roomId.observe(viewLifecycleOwner){
            Log.d("$$*********************","roomid = $it")
            if(it!= ""){
                roomId = it
            }else{
                viewModel.createRoom(order.userId, order.chefId,order.userName, order.chefName, order.userAvatar, order.chefAvatar)
            }
        }




        return root
    }



}