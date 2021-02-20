package com.evangelidis.loceat.restaurants

import com.evangelidis.loceat.Constant.CLIENT_ID
import com.evangelidis.loceat.Constant.CLIENT_SECRET
import com.evangelidis.loceat.Constant.RESPONSE_CATEGORY_ID
import com.evangelidis.loceat.Constant.RESPONSE_LIMIT
import com.evangelidis.loceat.Constant.VERSIONING
import com.evangelidis.loceat.api.ApiModule
import com.evangelidis.loceat.api.RestaurantsApi
import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantsRepository : RestaurantsContract.Model {

    private var apiClient: RestaurantsApi? = null

    init {
        apiClient = ApiModule.client.create(RestaurantsApi::class.java)
    }

    override fun loadRestaurants(latitude: Double, longitude: Double, listener: RestaurantsContract.APIListener) {
        val call = apiClient?.getVenues(CLIENT_ID, CLIENT_SECRET, VERSIONING, "$latitude,$longitude", RESPONSE_LIMIT, RESPONSE_CATEGORY_ID)

        call?.enqueue(object : Callback<RestaurantsResponse> {
            override fun onResponse(call: Call<RestaurantsResponse>, response: Response<RestaurantsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.response?.venues?.let {
                        listener.onSuccess(it)
                    }
                } else {
                    response.body()?.let {
                        listener.onError(it)
                    }
                }
            }

            override fun onFailure(call: Call<RestaurantsResponse>, t: Throwable) {
                listener.onFailure(t)
            }
        })
    }
}