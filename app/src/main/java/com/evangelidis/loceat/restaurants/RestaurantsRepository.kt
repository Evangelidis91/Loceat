package com.evangelidis.loceat.restaurants

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

    override fun loadRestaurants(listener: RestaurantsContract.APIListener) {
        val call = apiClient?.getVenues("SRPJKINSGQOLS24RLMSGUA31KBLWQIANSHMTT5Q3FWM55KHZ","UPWOLMUIRCE3M2W4GIFLUAF0SU0XQIW5JLY5WG1ICGMBZU1B",20180323, "38.054019,23.768462", 100, "4d4b7105d754a06374d81259" )

        call?.enqueue(object : Callback<RestaurantsResponse>{
            override fun onResponse(call: Call<RestaurantsResponse>, response: Response<RestaurantsResponse>) {
                if (response.isSuccessful){
                    response.body()?.response?.venues?.let {
                        listener.onSuccess(it)
                    }
                } else{
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