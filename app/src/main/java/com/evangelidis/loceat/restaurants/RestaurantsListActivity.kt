package com.evangelidis.loceat.restaurants

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.evangelidis.loceat.Constant
import com.evangelidis.loceat.R
import com.evangelidis.loceat.base.BaseActivity
import com.evangelidis.loceat.databinding.ActivityRestaurantsListBinding
import com.evangelidis.loceat.restaurants.model.Category
import com.evangelidis.loceat.restaurants.model.FormattedCategory
import com.evangelidis.loceat.restaurants.model.Venue

class RestaurantsListActivity : BaseActivity<RestaurantsContract.View, RestaurantsPresenter>(), RestaurantsContract.View, CategoriesAdapterCallback {

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

    private val binding: ActivityRestaurantsListBinding by lazy { ActivityRestaurantsListBinding.inflate(layoutInflater) }
    private val adapter: CategoriesAdapter by lazy { CategoriesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userLatitude = intent.getDoubleExtra(LATITUDE, 1000.0)
        userLongitude = intent.getDoubleExtra(LONGITUDE, 1000.0)

        if (userLatitude == 1000.0 || userLongitude == 1000.0) {
            userLatitude = Constant.DEFAULT_LATITUDE
            userLongitude = Constant.DEFAULT_LONGITUDE
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.extendedToolbar)
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor)

        presenter.loadRestaurants()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }


    override fun setRecyclerView(newsList: MutableList<Venue>) {
//        val f = newsList
//        val categories: MutableList<Category> = mutableListOf()
//
//        for (venue in f) {
//            for (category in venue.categories) {
//                if (!categories.contains(category)) {
//                    categories.add(category)
//                }
//            }
//        }
//
//        val y = categories
//        println(y)
//
//        val i = newsList.groupBy { it.categories.first().name }
//        println(i)

        newsList.sortBy { it.location?.distance }
        val grouped = newsList.groupBy { it.categories }
        val listToDeploy: MutableList<FormattedCategory> = mutableListOf()
        listToDeploy.add(FormattedCategory(type = "Space", venue = null, category = null))
        for (group in grouped) {
            listToDeploy.add(FormattedCategory(type = "Title", venue = null, category = group.key.first()))
            for (venue in group.value) {
                listToDeploy.add(FormattedCategory(type = "Venue", venue = venue, category = null))
            }
            listToDeploy.add(FormattedCategory(type = "Space", venue = null, category = null))
        }

        setAdapter(listToDeploy)
    }

    private fun setAdapter(listToDeploy: MutableList<FormattedCategory>){
        binding.venuesList.adapter = adapter
        adapter.venues = listToDeploy
    }


    override fun displayErrorMessage() {}

    override fun createPresenter() = RestaurantsPresenter()
    override fun performAction(action: String) {

    }
}

