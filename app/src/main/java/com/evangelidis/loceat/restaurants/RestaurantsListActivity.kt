package com.evangelidis.loceat.restaurants

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.evangelidis.loceat.Constant
import com.evangelidis.loceat.databinding.ActivityRestaurantsListBinding

class RestaurantsListActivity : AppCompatActivity() {

    private var userLatitude: Double? = null
    private var userLongitude: Double? = null

    companion object {

        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"

        fun createIntent(context: Context, latitude: Double, longitude: Double): Intent =
            Intent(context, RestaurantsListActivity::class.java)
                .putExtra(LATITUDE, latitude)
                .putExtra(LONGITUDE, longitude)
    }

    private val binding: ActivityRestaurantsListBinding by lazy {
        ActivityRestaurantsListBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userLatitude = intent.getDoubleExtra(LATITUDE, 1000.0)
        userLongitude = intent.getDoubleExtra(LONGITUDE, 1000.0)

        if (userLatitude == 1000.0 || userLongitude == 1000.0) {
            userLatitude = Constant.DEFAULT_LATITUDE
            userLongitude = Constant.DEFAULT_LONGITUDE
        }

    }
}