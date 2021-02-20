package com.evangelidis.loceat.api

import com.evangelidis.loceat.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {

    companion object {

        val client: Retrofit
            get() {
                return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
    }
}