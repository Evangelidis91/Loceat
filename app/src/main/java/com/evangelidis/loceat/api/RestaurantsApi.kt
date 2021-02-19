package com.evangelidis.loceat.api

import com.evangelidis.loceat.restaurants.model.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApi {

    @GET("venues/search")
    fun getVenues(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret:String,
        @Query("v") v:Int,
        @Query("ll") ll: String,
        @Query("limit") limit:Int,
        @Query("categoryId") categoryId:String
    ) : Call<RestaurantsResponse>
}