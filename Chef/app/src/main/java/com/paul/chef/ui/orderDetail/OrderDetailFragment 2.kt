package com.paul.chef.ui.orderDetail

import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.*
import com.paul.chef.data.Dish
import com.paul.chef.data.Order
import com.paul.chef.databinding.FragmentOrderDetailBinding
import com.paul.chef.databinding.ItemDisplayDishBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menuDetail.bindImage
import com.paul.chef.util.Util.getPrice
import java.time.LocalDate

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!
    private var _itemDisplayBinding: ItemDisplayDishBinding? = null
    private val itemDisplayBinding get() = _itemDisplayBinding!!
    private val displayList = mutableListOf<ItemDisplayDishBinding>()
    private val viewModel by viewModels<OrderDetailViewModel> { getVmFactory() }

    private val arg: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val order = arg.order
        var roomId = ""
        val mode = UserManger.readData("mode")

        binding.apply {
            val outlineProvider = ProfileOutlineProvider()
            imageView3.outlineProvider = outlineProvider
            when (mode) {
                Mode.CHEF.index -> {
                    bindImage(imageView3, order.userAvatar)
                    orderDetailType.text = getChefDetailTypeText(order.type)
                    orderDetailName.text = order.userName
                    orderDetailPaymentText.text = getString(R.string.your_receivement)
                    orderDetailTotal.text = getPrice(order.chefReceive)
                    setAcceptBtn(order)

                    orderDetailCancelBtn.text = when (order.status) {
                        OrderStatus.PENDING.index -> getString(R.string.refuse_order)
                        else -> getString(R.string.cancel_order)
                    }
                }
                Mode.USER.index -> {
                    bindImage(imageView3, order.chefAvatar)
                    orderDetailType.text = getUserDetailTypeText(order.type)
                    orderDetailName.text = order.chefName
                    orderDetailPaymentText.text = getString(R.string.your_payment)
                    orderDetailTotal.text = getPrice(order.userPay)
                    orderDetailAcceptBtn.visibility = View.GONE
                    orderDetailCancelBtn.text = getString(R.string.cancel_order)
                    setRatingBar(order)
                }
            }
            orderDetailStatus.text = getStatusText(order.status)
            orderDetailAddress.text = order.address.addressTxt
            orderDetailDate.text = LocalDate.ofEpochDay(order.date).toString()
            orderDetailTime.text = order.time
            orderDetailMenuName.text = order.menuName
            orderDetailNote.text = order.note
            orderDetailPeople.text = getString(R.string.people, order.people)
            orderDetailOrginalPrice.text = getPrice(order.originalPrice)
            orderDetailDiscount.text = getPrice(order.discount)
            orderDetailFee.text = getPrice(ChefManger().chefFee)

            orderDetailSendBtn.setOnClickListener {
                if (roomId != "") {
                    findNavController().navigate(
                        MobileNavigationDirections.actionGlobalChatRoomFragment(roomId)
                    )
                }
            }
        }

        setCancelBtn(order)

        showDishList(order.selectedDish, container)

        viewModel.getRoomId(order.userId, order.chefId)

        viewModel.roomId.observe(viewLifecycleOwner) {
            if (it != "") {
                roomId = it
            } else {
                viewModel.createRoom(
                    order.userId,
                    order.chefId,
                    order.userName,
                    order.chefName,
                    order.userAvatar,
                    order.chefAvatar
                )
            }
        }
        viewModel.acceptDone.observe(viewLifecycleOwner){
            if(it) findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
        }

        return root
    }

    private fun setAcceptBtn(order: Order) {
        if (order.status == OrderStatus.PENDING.index) {
            binding.orderDetailAcceptBtn.visibility = View.VISIBLE
        } else {
            binding.orderDetailAcceptBtn.visibility = View.GONE
        }
        binding.orderDetailAcceptBtn.setOnClickListener {
            viewModel.changeStatus(order.id, OrderStatus.UPCOMING.index)

        }
    }

    private fun getChefDetailTypeText(type: Int): CharSequence {
        return when (type) {
            BookType.ChefSpace.index -> BookType.ChefSpace.chefTxt
            BookType.UserSpace.index -> BookType.UserSpace.chefTxt
            else -> "something went wrong"
        }
    }

    private fun getUserDetailTypeText(type: Int): CharSequence {
        return when (type) {
            BookType.ChefSpace.index -> BookType.ChefSpace.userTxt
            BookType.UserSpace.index -> BookType.UserSpace.userTxt
            else -> "something went wrong"
        }
    }

    private fun setRatingBar(order: Order) {
        if (order.status == OrderStatus.COMPLETED.index) {
            binding.orderDetailRatingBar.visibility = View.VISIBLE
            binding.orderDetailReviewTxt.visibility = View.VISIBLE
            binding.orderDetailRatingBar.setOnRatingBarChangeListener { _, fl, _ ->
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalReviewFragment(
                        fl.toInt(),
                        order
                    )
                )
            }
        } else {
            binding.orderDetailRatingBar.visibility = View.GONE
            binding.orderDetailReviewTxt.visibility = View.GONE
        }
    }

    private fun getStatusText(status: Int): CharSequence {
        return when (status) {
            OrderStatus.PENDING.index -> OrderStatus.PENDING.value
            OrderStatus.UPCOMING.index -> OrderStatus.UPCOMING.value
            OrderStatus.COMPLETED.index -> OrderStatus.COMPLETED.value
            OrderStatus.CANCELLED.index -> OrderStatus.CANCELLED.value
            OrderStatus.SCORED.index -> OrderStatus.SCORED.value
            else -> "something went wrong"
        }
    }

    private fun setCancelBtn(order: Order) {
        when (order.status) {
            OrderStatus.PENDING.index, OrderStatus.UPCOMING.index -> {
                binding.orderDetailCancelBtn.isEnabled = true
                binding.orderDetailCancelBtn.setOnClickListener {
                    viewModel.changeStatus(order.id, OrderStatus.CANCELLED.index)
                    findNavController().navigate(
                        MobileNavigationDirections.actionGlobalOrderManageFragment()
                    )
                }
            }
            else -> {
                binding.orderDetailCancelBtn.isEnabled = false
            }
        }
    }

    private fun showDishList(selectedDish: List<Dish>, container: ViewGroup?) {
        var defaultType = -1
        val and = "and"
        displayList.clear()
        for (i in selectedDish) {
            _itemDisplayBinding =
                ItemDisplayDishBinding.inflate(LayoutInflater.from(context), container, false)
            if (i.typeNumber != defaultType) {
                // setType
                itemDisplayBinding.displayTitle.text = i.type
                defaultType = i.typeNumber
                binding.orderDetailLinear.addView(itemDisplayBinding.root)
                displayList.add(itemDisplayBinding)
            } else {
                // +and
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
            val name = if (i.extraPrice != 0) {
                "${i.name}    <I><font color = \"#03DAC5\">(+NT$ ${i.extraPrice})</font></I>"
            } else {
                "${i.name}"
            }
            nameText.text = Html.fromHtml(name)
            nameText.gravity = Gravity.CENTER
            displayList[defaultType].displayRG.addView(nameText)
            displayList[defaultType].displayRG.gravity = Gravity.CENTER
        }
    }
}
