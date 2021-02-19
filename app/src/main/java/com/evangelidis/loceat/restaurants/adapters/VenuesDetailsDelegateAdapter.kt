package com.evangelidis.loceat.restaurants.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evangelidis.loceat.databinding.ItemCategoryVenueBinding
import com.evangelidis.loceat.extensions.layoutInflater
import com.evangelidis.loceat.restaurants.CategoriesAdapterCallback
import com.evangelidis.loceat.restaurants.CategoriesDelegateAdapter
import com.evangelidis.loceat.restaurants.CategoriesDelegateViewHolder
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory

class VenuesDetailsDelegateAdapter : CategoriesDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): CategoriesDelegateViewHolder {
        return DetailsViewHolder(ItemCategoryVenueBinding.inflate(parent.layoutInflater(), parent, false))
    }

    class DetailsViewHolder(private val binding: ItemCategoryVenueBinding) : RecyclerView.ViewHolder(binding.root), CategoriesDelegateViewHolder{

        override fun bind(data: CategoriesViewType, callback: CategoriesAdapterCallback) {
            val section = data as? FormattedCategory ?: return

            binding.venueName.text = section.venue?.name
            binding.venueDistance.text = section.venue?.location?.distance.toString()
        }

    }
}