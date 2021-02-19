package com.evangelidis.loceat.restaurants

import android.view.ViewGroup
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory

interface CategoriesAdapterCallback {
    fun performAction(action: String)
}

interface CategoriesDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): CategoriesDelegateViewHolder
    fun onBindViewHolder(holder: CategoriesDelegateViewHolder, data: FormattedCategory, callback: CategoriesAdapterCallback) {
        holder.bind(data, callback)
    }
}

interface CategoriesDelegateViewHolder {
    fun bind(data: CategoriesViewType, callback: CategoriesAdapterCallback) {}
}
