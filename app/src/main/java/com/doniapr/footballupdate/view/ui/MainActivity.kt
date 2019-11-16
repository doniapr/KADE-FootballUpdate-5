package com.doniapr.footballupdate.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.leaguedetailadapter.ListLeagueAdapter
import com.doniapr.footballupdate.model.league.League
import com.doniapr.footballupdate.view.ui.detailleague.DetailLeagueActivity
import com.doniapr.footballupdate.view.ui.favorite.FavoriteActivity
import com.doniapr.footballupdate.view.ui.search.SearchResultActivity
import com.doniapr.footballupdate.view.ui.search.SearchResultActivity.Companion.QUERY
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var listItemLeague: RecyclerView
    private lateinit var toolbar: Toolbar

    private var listLeague: MutableList<League> = mutableListOf()
    private lateinit var adapter: ListLeagueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL

                toolbar = themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                    backgroundColor = ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                    title = resources.getString(R.string.app_name)
                }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize))

                listItemLeague = recyclerView {
                    id = R.id.rv_list_league
                    layoutManager = LinearLayoutManager(context)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        )

        setSupportActionBar(toolbar)
        setData()

        adapter =
            ListLeagueAdapter(listLeague) {
                this@MainActivity.startActivity<DetailLeagueActivity>(
                    DetailLeagueActivity.LEAGUE_ID to it.leagueId,
                    DetailLeagueActivity.LEAGUE_NAME to it.leagueName
                )
            }
        listItemLeague.adapter = adapter


    }

    private fun setData() {
        val leagueId = resources.getIntArray(R.array.league_id)
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueBadge = resources.getStringArray(R.array.league_badge)

        for (i in leagueName.indices) {
            val league = League(
                leagueId[i],
                leagueName[i],
                leagueBadge[i]
            )
            listLeague.add(league)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this@MainActivity.startActivity<SearchResultActivity>(QUERY to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                this@MainActivity.startActivity<FavoriteActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
