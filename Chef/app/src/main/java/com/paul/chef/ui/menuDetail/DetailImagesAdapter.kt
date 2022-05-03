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
import com.paul.chef.ImgRecyclerType
import com.paul.chef.MenuEditImg
import com.paul.chef.R
import com.paul.chef.data.Discount
import com.paul.chef.databinding.ItemAddedDiscountBinding
import com.paul.chef.databinding.ItemMenuDetailImagesBinding
import com.paul.chef.databinding.ItemMenuEditImgBinding

class DetailImagesAdapter(val type:Int,val menuEditImg: MenuEditImg?) : ListAdapter<String, RecyclerView.ViewHolder>(FriendListCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return ImageHolder.from(parent)
        return when(type){
            ImgRecyclerType.IMAGE.index->{
                ImageHolder.from(parent)
            }
            else->{
                ImageEditHolder.from(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

//        if (holder is ImageHolder) {
//            holder.bind(item)
//        }
        when(holder){
            is ImageHolder->{
                holder.bind(item)
            }
            is ImageEditHolder->{
                holder.bind(item, menuEditImg, position)
            }
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

    class ImageEditHolder(private var binding: ItemMenuEditImgBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: String, menuEditImg: MenuEditImg?, position: Int) {
            bindImage(binding.itemMenuEditImgView, item)
            binding.itemMeduEditImgDelete.setOnClickListener {
                menuEditImg?.remove(position)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ImageEditHolder {
                val image =
                    ItemMenuEditImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ImageEditHolder(image)
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
