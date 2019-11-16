package com.doniapr.footballupdate.view.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.pageradapter.FavoritePagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar_favorite)
        supportActionBar?.title = resources.getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favoritePagerAdapter =
            FavoritePagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager_favorite.adapter = favoritePagerAdapter
        tabs_favorite.setupWithViewPager(view_pager_favorite)
        supportActionBar?.elevation = 0f
    }
}
