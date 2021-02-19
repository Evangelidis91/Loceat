package com.evangelidis.loceat.restaurants.adapters

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import com.evangelidis.loceat.databinding.ItemCategoryTitleBinding
import com.evangelidis.loceat.extensions.gone
import com.evangelidis.loceat.extensions.layoutInflater
import com.evangelidis.loceat.extensions.show
import com.evangelidis.loceat.restaurants.CategoriesAdapterCallback
import com.evangelidis.loceat.restaurants.CategoriesDelegateAdapter
import com.evangelidis.loceat.restaurants.CategoriesDelegateViewHolder
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory

class VenuesCategoryTitleDelegateAdapter : CategoriesDelegateAdapter{

    override fun onCreateViewHolder(parent: ViewGroup): CategoriesDelegateViewHolder {
        return TitleViewHolder(ItemCategoryTitleBinding.inflate(parent.layoutInflater(), parent, false))
    }

    class TitleViewHolder(private val binding: ItemCategoryTitleBinding) : RecyclerView.ViewHolder(binding.root), CategoriesDelegateViewHolder{

        override fun bind(data: CategoriesViewType, callback: CategoriesAdapterCallback) {
            val section = data as? FormattedCategory ?: return

            binding.categoryTitle.text = section.category?.name

            val imageUrl = "https://foursquare.com/img/categories/food/"+section.category?.icon?.prefix?.substringAfterLast("/")?.replace("_", section.category?.icon?.suffix.toString())

            Glide.with(binding.root)
                .load(imageUrl)
                .listener(object: com.bumptech.glide.request.RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        binding.categoryImage.gone()
                        return false
                    }
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        binding.categoryImage.show()
                        return false
                    }
                })
                .into(binding.categoryImage)
        }

    }

}