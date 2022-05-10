package com.paul.chef.ui.menuDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paul.chef.Block
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.R
import com.paul.chef.data.Review
import com.paul.chef.databinding.ItemReviewBinding

class ReviewAdapter(val block: Block): ListAdapter<Review, RecyclerView.ViewHolder>(ReviewListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is ReviewHolder) {
            holder.bind(item, block)
        }

    }


    class ReviewHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Review, block:Block) {
            val outlineProvider = ProfileOutlineProvider()
            binding.itemReviewAvatar.outlineProvider =outlineProvider
            bindImage(binding.itemReviewAvatar, item.userAvatar)
            binding.itemReviewContent.text = item.content
            binding.itemReviewUserName.text = item.userName
            binding.ratingBar3.isIndicator
            binding.ratingBar3.rating = item.rating
            binding.itemReviewMore.setOnClickListener {
                val popupMenu = PopupMenu(binding.root.context, it)
                popupMenu.setOnMenuItemClickListener { menuItem->
                    when(menuItem.itemId){
                         R.id.menu_block->{
                           block.blockReview(item.userId)
                             true
                        }
                        else->{
                            false
                        }
                    }
                }
                popupMenu.inflate(R.menu.menu_block)
                popupMenu.show()
            }

        }

        companion object {
            fun from(parent: ViewGroup): ReviewHolder {
                val image =
                    ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ReviewHolder(image)
            }
        }
    }

}

class ReviewListCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}
//
//fun bindImage(imgView: ImageView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//        Glide.with(imgView.context)
//            .load(imgUri)
//            .into(imgView)
//    }
//}
