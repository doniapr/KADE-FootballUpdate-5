package com.doniapr.footballupdate.view.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.pageradapter.SearchResultPagerAdapter
import com.doniapr.footballupdate.view.ui.detailleague.DetailLeagueActivity.Companion.LEAGUE_NAME
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.startActivity

class SearchResultActivity : AppCompatActivity() {

    companion object {
        const val QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val intent = intent
        val query = intent.getStringExtra(QUERY)
        val leagueName = intent.getStringExtra(LEAGUE_NAME)

        setSupportActionBar(toolbar_search)
        supportActionBar?.title = resources.getString(R.string.search_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val searchResultViewPager =
            SearchResultPagerAdapter(
                this,
                supportFragmentManager,
                query,
                leagueName
            )
        view_pager_search.adapter = searchResultViewPager
        tabs_search.setupWithViewPager(view_pager_search)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this@SearchResultActivity.startActivity<SearchResultActivity>(
                    QUERY to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}
