package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.base.BasePresenter
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import com.evangelidis.loceat.restaurants.model.Venue

class RestaurantsPresenter : BasePresenter<RestaurantsContract.View>(), RestaurantsContract.Presenter, RestaurantsContract.APIListener {

    override fun loadRestaurants(latitude: Double, longitude: Double, loadingText: String) {
        view?.showLoader(loadingText)
        RestaurantsRepository().loadRestaurants(latitude, longitude, this)
    }

    override fun onSuccess(venuesList: MutableList<Venue>) {
        view?.setRecyclerView(venuesList)
        view?.hideLoader()
    }

    override fun onError(venuesList: RestaurantsResponse) {
        view?.displayErrorMessage()
    }

    override fun onFailure(t: Throwable?) {
        view?.displayErrorMessage()
    }
}