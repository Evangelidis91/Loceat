package com.evangelidis.loceat.restaurants.model

import androidx.annotation.Keep
import kotlinx.android.parcel.IgnoredOnParcel

data class FormattedCategory(
    val type: String,
    val venue: Venue?,
    val category: Category?
) : CategoriesViewType {

    @IgnoredOnParcel
    override val viewType: Int = when (type) {
        CategoriesViewType.CATEGORY_TITLE_TYPE -> CategoriesViewType.TITLE
        CategoriesViewType.CATEGORY_VENUE_TYPE -> CategoriesViewType.VENUE
        CategoriesViewType.CATEGORY_SPACE_TYPE -> CategoriesViewType.SPACE
        else -> -1
    }
}

@Keep
interface CategoriesViewType {
    companion object {
        const val TITLE = 0
        const val VENUE = 1
        const val SPACE = 2

        const val CATEGORY_TITLE_TYPE = "Title"
        const val CATEGORY_VENUE_TYPE = "Venue"
        const val CATEGORY_SPACE_TYPE = "Space"
    }

    val viewType: Int
}