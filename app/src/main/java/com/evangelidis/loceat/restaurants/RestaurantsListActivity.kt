package com.evangelidis.loceat.restaurants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.evangelidis.loceat.Constant
import com.evangelidis.loceat.ItemsManager.getUserAddress
import com.evangelidis.loceat.R
import com.evangelidis.loceat.base.BaseActivity
import com.evangelidis.loceat.databinding.ActivityRestaurantsListBinding
import com.evangelidis.loceat.extensions.gone
import com.evangelidis.loceat.restaurants.model.CategoriesViewType.Companion.CATEGORY_SPACE_TYPE
import com.evangelidis.loceat.restaurants.model.CategoriesViewType.Companion.CATEGORY_TITLE_TYPE
import com.evangelidis.loceat.restaurants.model.CategoriesViewType.Companion.CATEGORY_VENUE_TYPE
import com.evangelidis.loceat.restaurants.model.FormattedCategory
import com.evangelidis.loceat.restaurants.model.Venue

class RestaurantsListActivity : BaseActivity<RestaurantsContract.View, RestaurantsPresenter>(), RestaurantsContract.View, CategoriesAdapterCallback {

    companion object {

        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"

        fun createIntent(context: Context, latitude: Double, longitude: Double): Intent =
            Intent(context, RestaurantsListActivity::class.java)
                .putExtra(LATITUDE, latitude)
                .putExtra(LONGITUDE, longitude)
    }

    private val binding: ActivityRestaurantsListBinding by lazy { ActivityRestaurantsListBinding.inflate(layoutInflater) }
    private val adapter: CategoriesAdapter by lazy { CategoriesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userLatitude = intent.getDoubleExtra(LATITUDE, Constant.DEFAULT_LATITUDE)
        val userLongitude = intent.getDoubleExtra(LONGITUDE, Constant.DEFAULT_LONGITUDE)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setToolbar(userLatitude, userLongitude)

        presenter.loadRestaurants(latitude = userLatitude, longitude = userLongitude, loadingText = getString(R.string.loading_venues_text))
    }

    private fun setToolbar(latitude: Double, longitude: Double) {
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.extendedToolbar)
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor)

        val addressesList = getUserAddress(this, latitude, longitude).split(",").toTypedArray()
        when {
            addressesList.count() > 1 -> {
                binding.collapsingToolbar.title = addressesList.first()
                binding.toolbarAddressText.text = addressesList[1]
            }
            addressesList.count() == 1 -> {
                binding.collapsingToolbar.title = addressesList.first()
                binding.toolbarAddressText.gone()
            }
            else -> {
                binding.collapsingToolbar.title = getString(R.string.unknown_location_text)
                binding.toolbarAddressText.gone()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun setRecyclerView(venuesList: MutableList<Venue>) {
        if (venuesList.isNullOrEmpty()) {
            errorToastMessage()
        }
        venuesList.sortBy { it.location.distance }
        val grouped = venuesList.groupBy { it.categories }
        val listToDeploy: MutableList<FormattedCategory> = mutableListOf()
        listToDeploy.add(FormattedCategory(type = CATEGORY_SPACE_TYPE, venue = null, category = null))
        for (group in grouped) {
            listToDeploy.add(FormattedCategory(type = CATEGORY_TITLE_TYPE, venue = null, category = group.key.first()))
            for (venue in group.value) {
                listToDeploy.add(FormattedCategory(type = CATEGORY_VENUE_TYPE, venue = venue, category = null))
            }
            listToDeploy.add(FormattedCategory(type = CATEGORY_SPACE_TYPE, venue = null, category = null))
        }
        setAdapter(listToDeploy)
    }

    private fun setAdapter(listToDeploy: MutableList<FormattedCategory>) {
        binding.venuesList.adapter = adapter
        adapter.venues = listToDeploy
    }

    override fun displayErrorMessage() {
        errorToastMessage()
    }

    private fun errorToastMessage() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun navigateToVenue(venueId: String) {
        Toast.makeText(this, getString(R.string.navigate_to_venue_toast).replace("{ID}", venueId), Toast.LENGTH_SHORT).show()
    }

    override fun createPresenter() = RestaurantsPresenter()

}