package com.evangelidis.loceat.restaurants.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evangelidis.loceat.Constant.BASE_CATEGORY_IMAGE_URL
import com.evangelidis.loceat.ItemsManager.getImage
import com.evangelidis.loceat.databinding.ItemCategoryTitleBinding
import com.evangelidis.loceat.extensions.hide
import com.evangelidis.loceat.extensions.layoutInflater
import com.evangelidis.loceat.restaurants.CategoriesAdapterCallback
import com.evangelidis.loceat.restaurants.CategoriesDelegateAdapter
import com.evangelidis.loceat.restaurants.CategoriesDelegateViewHolder
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory

class VenuesCategoryTitleDelegateAdapter : CategoriesDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): CategoriesDelegateViewHolder {
        return TitleViewHolder(ItemCategoryTitleBinding.inflate(parent.layoutInflater(), parent, false))
    }

    class TitleViewHolder(private val binding: ItemCategoryTitleBinding) : RecyclerView.ViewHolder(binding.root), CategoriesDelegateViewHolder {

        override fun bind(data: CategoriesViewType, callback: CategoriesAdapterCallback) {
            val section = data as? FormattedCategory ?: return

            binding.categoryTitle.text = section.category?.name

            section.category?.icon?.let {
                if (!it.prefix.isNullOrEmpty() && !it.suffix.isNullOrEmpty()) {
                    val imageUrl = BASE_CATEGORY_IMAGE_URL + it.prefix.substringAfterLast("/").replace("_", it.suffix)
                    getImage(binding.root.context, imageUrl, binding.categoryImage)
                } else {
                    binding.categoryImage.hide()
                }
            }
        }
    }
}