package com.doniapr.footballupdate.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.ListLeagueAdapter
import com.doniapr.footballupdate.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
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

                toolbar = toolbar {
                    backgroundColor = ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryDark
                    )
                    title = resources.getString(R.string.app_name)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                listItemLeague = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        )

        setSupportActionBar(toolbar)
        setData()

        adapter = ListLeagueAdapter(listLeague)
        listItemLeague.adapter = adapter


    }

    private fun setData() {

        val leagueId = resources.getIntArray(R.array.league_id)
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueBadge = resources.getStringArray(R.array.league_badge)

        for (i in leagueName.indices) {
            Log.e("liga", leagueName[i])
            val league = League(leagueId[i], leagueName[i], leagueBadge[i])

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
                this@MainActivity.startActivity<SearchResultActivity>("query" to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

}
