package com.evangelidis.loceat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target
import com.evangelidis.loceat.Constant.GEOLOCATION_LANGUAGE
import com.evangelidis.loceat.extensions.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.*

object ItemsManager {

    fun isPermissionsGranted(context: Context) =
        ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    fun getImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    imageView.hide()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    imageView.show()
                    return false
                }
            })
            .into(imageView)
    }

    fun setDistance(context: Context, meters: Int): String {
        val solution: Double = Math.round((meters.toDouble() / 1000) * 10.0) / 10.0
        return context.getString(R.string.distance_format).replace("{KM}", solution.toString())
    }

    fun getUserAddress(context: Context, latitude: Double, longitude: Double): String {
        val aLocale: Locale = Locale.Builder().setLanguage(GEOLOCATION_LANGUAGE).build()
        val geocoder = Geocoder(context, aLocale)
        val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses.isNullOrEmpty()){
            return context.getString(R.string.unknown_location_text)
        }
        return addresses.first().getAddressLine(0)
    }

    fun setMarkerIcon(context: Context): BitmapDescriptor? {
        val height = context.dimen(R.dimen.marker_size)
        val width = context.dimen(R.dimen.marker_size)
        val bitmapdraw = context.resources.getDrawable(R.drawable.icn_location_ping) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(smallMarker)
    }

    fun isConnected(context: Context): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
        } catch (e: Exception) {
            Log.v("connectivity", e.toString())
        }
        return false
    }
}