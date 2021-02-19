package com.evangelidis.loceat.restaurants.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evangelidis.loceat.databinding.ItemCategorySpaceBinding
import com.evangelidis.loceat.extensions.layoutInflater
import com.evangelidis.loceat.restaurants.CategoriesDelegateAdapter
import com.evangelidis.loceat.restaurants.CategoriesDelegateViewHolder

class VenuesSpaceDelegateAdapter : CategoriesDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): CategoriesDelegateViewHolder {
        return SpaceViewHolder(ItemCategorySpaceBinding.inflate(parent.layoutInflater(),parent, false))
    }

    class SpaceViewHolder(binding: ItemCategorySpaceBinding) : RecyclerView.ViewHolder(binding.root), CategoriesDelegateViewHolder
}