package com.evangelidis.loceat.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.evangelidis.loceat.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}
