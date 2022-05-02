package com.paul.chef.ui.menu

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.ImgRecyclerType
import com.paul.chef.ItemMenu
import com.paul.chef.MenuType
import com.paul.chef.data.ChefMenu
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.databinding.ItemMenuSimpleBinding
import com.paul.chef.ui.menuDetail.DetailImagesAdapter
import com.paul.chef.ui.menuDetail.bindImage
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class MenuListAdapter(private val itemMenu:ItemMenu?, private val menuViewModel:MenuListViewModel?, val type:Int) : ListAdapter<ChefMenu, RecyclerView.ViewHolder>(MenuCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return FullHolder.from(parent)
        return when(type){
            MenuType.FULL.index-> FullHolder.from(parent)
            else -> SimpleHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when(holder){
            is FullHolder->{
                holder.bind(item, itemMenu, menuViewModel)
            }
            is SimpleHolder ->{
                holder.bind(item)
            }
        }

    }




    class FullHolder(private var binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageAdapter: DetailImagesAdapter
        private var layoutManager: RecyclerView.LayoutManager? = null

        init{
            val context = itemView.context
            imageAdapter = DetailImagesAdapter(ImgRecyclerType.IMAGE.index, null)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.itemMenuImgRecycler.layoutManager = layoutManager
            binding.itemMenuImgRecycler.adapter = imageAdapter
        }

        fun bind(item: ChefMenu, itemMenu: ItemMenu?, menuViewModel: MenuListViewModel?) {
            if(itemMenu!=null){

                itemView.setOnClickListener {
                    itemMenu.goDetail(item)
                }
                imageAdapter.submitList(item.images)


                binding.itemMenuLikeCheck.isChecked = menuViewModel?.likeIdList?.value?.contains(item.id) == true
                binding.itemMenuLikeCheck.setOnClickListener{
                    itemMenu.like(item.id)
                }
                binding.menuTitle.text = item.menuName
                if (item.reviewRating!=null){
                    binding.ratingBar2.visibility = View.VISIBLE
                    binding.itemMenuRatingNum.visibility = View.VISIBLE
                    binding.itemMenuRatingNum.text = item.reviewRating.toString()
                }else{
                    binding.ratingBar2.visibility = View.GONE
                    binding.itemMenuRatingNum.visibility = View.GONE
                }
                binding.itemMenuPerPrice.text = "NT$"+item.perPrice.toString()+"/人"

            }
        }

        companion object {
            fun from(parent: ViewGroup): FullHolder {
                val menu =
                    ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FullHolder(menu)
            }
        }
    }


    class SimpleHolder(private var binding: ItemMenuSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChefMenu) {
//            itemView.setOnClickListener {
//                itemMenu.goDetail(item)
//            }


            binding.itemMenuSimpleTitle.text = item.menuName
            bindImage(binding.itemMenuSimpleImg, item.images[0])

        }

        companion object {
            fun from(parent: ViewGroup): SimpleHolder {
                val menu =
                    ItemMenuSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SimpleHolder(menu)
            }
        }
    }




}

class MenuCallback : DiffUtil.ItemCallback<ChefMenu>() {
    override fun areItemsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChefMenu, newItem: ChefMenu): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}
