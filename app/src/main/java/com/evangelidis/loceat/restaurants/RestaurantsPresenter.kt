package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.base.BasePresenter
import com.evangelidis.loceat.restaurants.model.CategoriesViewType
import com.evangelidis.loceat.restaurants.model.FormattedCategory
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import com.evangelidis.loceat.restaurants.model.Venue

class RestaurantsPresenter : BasePresenter<RestaurantsContract.View>(), RestaurantsContract.Presenter, RestaurantsContract.APIListener {

    override fun loadRestaurants(latitude: Double, longitude: Double, loadingText: String) {
        view?.showLoader(loadingText)
        RestaurantsRepository().loadRestaurants(latitude, longitude, this)
    }

    override fun onSuccess(venuesList: MutableList<Venue>) {
        view?.setRecyclerView(generateGroupedList(venuesList))
        view?.hideLoader()
    }

    override fun onError(venuesList: RestaurantsResponse) {
        view?.displayErrorMessage()
    }

    override fun onFailure(t: Throwable?) {
        view?.displayErrorMessage()
    }

    private fun generateGroupedList(venuesList: MutableList<Venue>): MutableList<FormattedCategory> {
        val listToDeploy: MutableList<FormattedCategory> = mutableListOf()
        if (venuesList.isNullOrEmpty()) {
            view?.displayErrorMessage()
        } else {
            venuesList.sortBy { it.location.distance }
            val grouped = venuesList.groupBy { it.categories }

            listToDeploy.add(FormattedCategory(type = CategoriesViewType.CATEGORY_SPACE_TYPE, venue = null, category = null))
            for (group in grouped) {
                listToDeploy.add(FormattedCategory(type = CategoriesViewType.CATEGORY_TITLE_TYPE, venue = null, category = group.key.first()))
                for (venue in group.value) {
                    listToDeploy.add(FormattedCategory(type = CategoriesViewType.CATEGORY_VENUE_TYPE, venue = venue, category = null))
                }
                listToDeploy.add(FormattedCategory(type = CategoriesViewType.CATEGORY_SPACE_TYPE, venue = null, category = null))
            }
        }
        return listToDeploy
    }
}