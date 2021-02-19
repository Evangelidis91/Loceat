package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.base.BaseContract
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import com.evangelidis.loceat.restaurants.model.Venue

interface RestaurantsContract {

    interface Model{
        fun loadRestaurants(listener: APIListener)
    }

    interface View : BaseContract.View{
        fun setRecyclerView(newsList: MutableList<Venue>)
        fun displayErrorMessage()
    }

    interface Presenter{
        fun loadRestaurants()
    }

    interface APIListener {
        fun onSuccess(newsList: MutableList<Venue>)
        fun onError(restaurants: RestaurantsResponse)
        fun onFailure(t: Throwable?)
    }
}