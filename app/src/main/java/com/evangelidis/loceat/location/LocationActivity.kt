package com.evangelidis.loceat.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.evangelidis.loceat.Constant
import com.evangelidis.loceat.Constant.DEFAULT_LATITUDE
import com.evangelidis.loceat.Constant.DEFAULT_LONGITUDE
import com.evangelidis.loceat.Constant.GPS_REQUEST
import com.evangelidis.loceat.Constant.LOCATION_REQUEST
import com.evangelidis.loceat.ItemsManager.getUserAddress
import com.evangelidis.loceat.ItemsManager.isPermissionsGranted
import com.evangelidis.loceat.ItemsManager.setMarkerIcon
import com.evangelidis.loceat.R
import com.evangelidis.loceat.base.BaseActivity
import com.evangelidis.loceat.base.BaseContract
import com.evangelidis.loceat.base.BasePresenter
import com.evangelidis.loceat.databinding.ActivityLocationBinding
import com.evangelidis.loceat.restaurants.RestaurantsListActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class LocationActivity : BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>(), OnMapReadyCallback {

    companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, LocationActivity::class.java)
    }

    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    private val binding: ActivityLocationBinding by lazy { ActivityLocationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.view?.showLoader(getString(R.string.tracking_location_text))

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                this@LocationActivity.isGPSEnabled = isGPSEnable
            }
        })
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> setLocation(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

            isPermissionsGranted(this) -> startLocationUpdate()

            else -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_REQUEST)
        }
    }

    private fun startLocationUpdate() {
        locationViewModel.getLocationData().observe(this, Observer {
            setLocation(it.latitude, it.longitude)
        })
    }

    private fun setLocation(latitude: Double, longitude: Double) {
        lat = latitude
        lng = longitude

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                if (isPermissionsGranted(this)) {
                    invokeLocationAction()
                } else {
                    setLocation(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val position = LatLng(lat, lng)

        googleMap.apply {
            clear()
            addMarker(MarkerOptions().position(position).icon(setMarkerIcon(this@LocationActivity)))
            uiSettings.isMyLocationButtonEnabled = false
            moveCamera(CameraUpdateFactory.newLatLng(position))
            animateCamera(CameraUpdateFactory.newLatLngZoom(position, Constant.GOOGLE_MAP_ZOOM_LEVEL))
        }

        binding.userAddress.text = getUserAddress(this, lat, lng)
        binding.continueText.setOnClickListener {
            startActivity(RestaurantsListActivity.createIntent(this, lat, lng))
        }
        binding.centerTarget.setOnClickListener {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), Constant.GOOGLE_MAP_ZOOM_LEVEL))
        }

        presenter.view?.hideLoader()
    }

    override fun createPresenter(): BasePresenter<BaseContract.View> = BasePresenter()

}