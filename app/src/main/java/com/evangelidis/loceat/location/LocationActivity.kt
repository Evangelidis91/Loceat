package com.evangelidis.loceat.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.evangelidis.loceat.Constant
import com.evangelidis.loceat.Constant.GPS_REQUEST
import com.evangelidis.loceat.Constant.LOCATION_REQUEST
import com.evangelidis.loceat.base.BaseActivity
import com.evangelidis.loceat.base.BaseContract
import com.evangelidis.loceat.base.BasePresenter
import com.evangelidis.loceat.databinding.ActivityLocationBinding
import com.evangelidis.loceat.extensions.show
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*


class LocationActivity :  BaseActivity<BaseContract.View, BasePresenter<BaseContract.View>>() {

    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false

    private val binding : ActivityLocationBinding by lazy{ActivityLocationBinding.inflate(
        layoutInflater
    )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.view?.showLoader()

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        if (Prefs.getBoolean(Constant.PREFS_USER_LOCATION_PERMISSION, true)) {
            GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {
                override fun gpsStatus(isGPSEnable: Boolean) {
                    this@LocationActivity.isGPSEnabled = isGPSEnable
                }
            })
        }
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
//            !isGPSEnabled -> {
//                location.text = "Athens"
//            }

            isPermissionsGranted() -> startLocationUpdate()

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        locationViewModel.getLocationData().observe(this, Observer {
            val aLocale: Locale = Locale.Builder().setLanguage("el").build()
            val geocoder = Geocoder(this, aLocale)
            val addresses: List<Address> = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            binding.userAddress.text = addresses.first().getAddressLine(0)

            presenter.view?.hideLoader()
            binding.map.show()
            binding.map.addMarker(it.latitude, it.longitude)
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                Prefs.putBoolean(Constant.PREFS_USER_LOCATION_PERMISSION, isPermissionsGranted())
                if (isPermissionsGranted()) {
                    invokeLocationAction()
                } else {
                    binding.userAddress.text = "Athens"
                }
            }
        }
    }

    override fun createPresenter(): BasePresenter<BaseContract.View> = BasePresenter()

}