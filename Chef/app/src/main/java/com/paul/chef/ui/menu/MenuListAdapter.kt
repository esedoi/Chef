package com.paul.chef.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.paul.chef.*
import com.paul.chef.data.Menu
import com.paul.chef.databinding.ItemMenuListBinding
import com.paul.chef.databinding.ItemMenuSimpleBinding
import com.paul.chef.ui.menuDetail.DetailImagesAdapter
import com.paul.chef.ui.menuDetail.bindImage
import com.paul.chef.util.Util.getPerPrice

class MenuListAdapter(
    private val itemMenu: ItemMenu?,
    private val menuViewModel: MenuListViewModel?,
    val type: Int,
) : ListAdapter<Menu, RecyclerView.ViewHolder>(MenuCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            MenuType.FULL.index -> FullHolder.from(parent)
            else -> SimpleHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is FullHolder -> {
                holder.bind(item, itemMenu, menuViewModel)
            }
            is SimpleHolder -> {
                holder.bind(item, itemMenu)
            }
        }
    }

    class FullHolder(private var binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageAdapter: DetailImagesAdapter
        private var layoutManager: RecyclerView.LayoutManager? = null

        fun bind(item: Menu, itemMenu: ItemMenu?, menuViewModel: MenuListViewModel?) {
            val context = itemView.context
            imageAdapter = DetailImagesAdapter(ImgRecyclerType.IMAGE.index, null, itemMenu!!, item)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.itemMenuImgRecycler.layoutManager = layoutManager
            binding.itemMenuImgRecycler.adapter = imageAdapter
            binding.itemMenuImgRecycler.onFlingListener = null
            val linearSnapHelper = LinearSnapHelper().apply {
                attachToRecyclerView(binding.itemMenuImgRecycler)
            }
            binding.itemMenuImgRecycler.setOnScrollChangeListener { _, _, _, _, _ ->
                val snapView = linearSnapHelper.findSnapView(
                    binding.itemMenuImgRecycler.layoutManager
                )
                if (snapView != null) {
                    val position = binding.itemMenuImgRecycler.layoutManager?.getPosition(snapView)
                    binding.itemMenuPositionTxt.text = "${position?.plus(1)}/${item.images.size}"
                }
            }

            itemView.setOnClickListener {
                itemMenu.goDetail(item)
            }

            imageAdapter.submitList(item.images)
            val outlineProvider = ProfileOutlineProvider()
            binding.itemMenuChefAvatar.outlineProvider = outlineProvider
            bindImage(binding.itemMenuChefAvatar, item.chefAvatar)

            binding.itemMenuLikeCheck.isChecked =
                menuViewModel?.likeIdList?.value?.contains(item.id) == true
            binding.itemMenuLikeCheck.setOnClickListener {
                itemMenu.like(item.id)
            }
            binding.menuTitle.text = item.menuName
            if (item.reviewRating != null) {
                binding.ratingBar2.visibility = View.VISIBLE
                binding.itemMenuRatingNum.visibility = View.VISIBLE
                binding.itemMenuRating.visibility = View.VISIBLE
                binding.ratingBar2.rating = item.reviewRating
                val str: String = String.format("%.1f", item.reviewRating)
                binding.itemMenuRating.text = str
                binding.itemMenuRatingNum.text =
                    binding.root.context.getString(R.string.number_of_review, item.reviewNumber)
            } else {
                binding.itemMenuRating.visibility = View.GONE
                binding.ratingBar2.visibility = View.GONE
                binding.itemMenuRatingNum.visibility = View.GONE
            }
            binding.itemMenuPerPrice.text = getPerPrice(item.perPrice)
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

        fun bind(item: Menu, itemMenu: ItemMenu?) {
            itemView.setOnClickListener {
                itemMenu?.goDetail(item)
            }

            binding.itemMenuSimpleTitle.text = item.menuName
            bindImage(binding.itemMenuSimpleImg, item.images[0])
        }

        companion object {
            fun from(parent: ViewGroup): SimpleHolder {
                val menu =
                    ItemMenuSimpleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return SimpleHolder(menu)
            }
        }
    }
}

class MenuCallback : DiffUtil.ItemCallback<Menu>() {
    override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
