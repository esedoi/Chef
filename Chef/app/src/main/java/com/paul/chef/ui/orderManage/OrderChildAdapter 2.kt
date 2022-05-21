package com.paul.chef.ui.orderManage


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.GoOrderDetail
import com.paul.chef.Mode
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.R
import com.paul.chef.data.Order
import com.paul.chef.databinding.ItemOrderChildBinding
import com.paul.chef.ui.menuDetail.bindImage
import java.time.LocalDate

class OrderChildAdapter(private val goOrderDetail: GoOrderDetail, val mode:Int) : ListAdapter<Order, RecyclerView.ViewHolder>(OrderCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is OrderHolder) {
            holder.bind(item,goOrderDetail, mode)
        }

    }


    class OrderHolder(private var binding: ItemOrderChildBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Order, goOrderDetail: GoOrderDetail, mode:Int) {
            val outlineProvider = ProfileOutlineProvider()
            binding.imageView6.outlineProvider =outlineProvider

            when(mode){
                Mode.CHEF.index->{
                    binding.itemOrderName.text = item.userName
                    bindImage(binding.imageView6, item.userAvatar)
                }
                Mode.USER.index->{
                    binding.itemOrderName.text = item.chefName
                    bindImage(binding.imageView6, item.chefAvatar)
                }
            }
            binding.itemOrderCardView.setOnClickListener {
                goOrderDetail.goDetail(item)
            }


            val localDate: LocalDate = LocalDate.ofEpochDay(item.date)
            binding.orderDate.text = localDate.toString()
            binding.orderTime.text = item.time
            binding.orderPeople.text = binding.root.context.getString(R.string.people, item.people)
            binding.orderMenu.text = item.menuName
        }

        companion object {
            fun from(parent: ViewGroup): OrderHolder {
                val order =
                    ItemOrderChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return OrderHolder(order)
            }
        }
    }

}

class OrderCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
