package com.paul.chef.ui.menuDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paul.chef.AddDiscount
import com.paul.chef.R
import com.paul.chef.data.Discount
import com.paul.chef.databinding.ItemAddedDiscountBinding
import com.paul.chef.databinding.ItemMenuDetailImagesBinding

class DetailImagesAdapter : ListAdapter<String, RecyclerView.ViewHolder>(FriendListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is ImageHolder) {
            holder.bind(item)
        }

    }


    class ImageHolder(private var binding: ItemMenuDetailImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: String) {
            bindImage(binding.detailImage, item)
        }

        companion object {
            fun from(parent: ViewGroup): ImageHolder {
                val image =
                    ItemMenuDetailImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ImageHolder(image)
            }
        }
    }

}

class FriendListCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }


}

fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}
