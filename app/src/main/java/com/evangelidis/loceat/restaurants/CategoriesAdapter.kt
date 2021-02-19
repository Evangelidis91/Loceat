package com.evangelidis.loceat.restaurants

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evangelidis.loceat.restaurants.adapters.VenuesCategoryTitleDelegateAdapter
import com.evangelidis.loceat.restaurants.adapters.VenuesDetailsDelegateAdapter
import com.evangelidis.loceat.restaurants.adapters.VenuesSpaceDelegateAdapter
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory
import java.lang.IllegalStateException

class CategoriesAdapter(private val callback: CategoriesAdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegateAdapters: Map<Int, CategoriesDelegateAdapter> = mapOf(
        CategoriesViewType.TITLE to VenuesCategoryTitleDelegateAdapter(),
        CategoriesViewType.VENUE to VenuesDetailsDelegateAdapter(),
        CategoriesViewType.SPACE to VenuesSpaceDelegateAdapter()
    )

    var venues = mutableListOf<FormattedCategory>()
        set(value) {
            venues.clear()
            venues.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegateAdapters[viewType]?.onCreateViewHolder(parent) as? RecyclerView.ViewHolder ?: throw IllegalStateException("Unknown view type $viewType")

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CategoriesDelegateViewHolder)?.let {
            delegateAdapters[getItemViewType(position)]?.onBindViewHolder(it, venues[position], callback) ?: throw IllegalStateException("Unknown holder $holder")
        }
    }

    override fun getItemCount(): Int = venues.count()

    override fun getItemViewType(position: Int): Int = venues[position].viewType
}