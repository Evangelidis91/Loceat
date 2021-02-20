package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.base.BaseContract
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import com.evangelidis.loceat.restaurants.model.Venue

interface RestaurantsContract {

    interface Model {
        fun loadRestaurants(latitude: Double, longitude: Double, listener: APIListener)
    }

    interface View : BaseContract.View {
        fun setRecyclerView(venuesList: MutableList<Venue>)
        fun displayErrorMessage()
    }

    interface Presenter {
        fun loadRestaurants(latitude: Double, longitude: Double, loadingText: String)
    }

    interface APIListener {
        fun onSuccess(venuesList: MutableList<Venue>)
        fun onError(venuesList: RestaurantsResponse)
        fun onFailure(t: Throwable?)
    }
}