package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.base.BasePresenter
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import com.evangelidis.loceat.restaurants.model.Venue

class RestaurantsPresenter : BasePresenter<RestaurantsContract.View>(), RestaurantsContract.Presenter, RestaurantsContract.APIListener {

    override fun loadRestaurants() {
        view?.showLoader()
        RestaurantsRepository().loadRestaurants(this)
    }

    override fun onSuccess(newsList: MutableList<Venue>) {
        view?.setRecyclerView(newsList)
        view?.hideLoader()
    }

    override fun onError(restaurants: RestaurantsResponse) {
        view?.displayErrorMessage()
    }

    override fun onFailure(t: Throwable?) {
        view?.displayErrorMessage()
    }
}