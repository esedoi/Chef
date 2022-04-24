package com.paul.chef.ui.menuDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paul.chef.data.Review
import com.paul.chef.databinding.ItemReviewBinding

class ReviewAdapter: ListAdapter<Review, RecyclerView.ViewHolder>(ReviewListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is ReviewHolder) {
            holder.bind(item)
        }

    }


    class ReviewHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Review) {
            binding.itemReviewContent.text = item.content
            binding.itemReviewUserName.text = item.userName
            binding.ratingBar3.isIndicator
            binding.ratingBar3.rating = item.rating

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
