package com.doniapr.footballupdate.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.SearchResultAdapter
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.presenter.SearchPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.SearchResultView
import com.doniapr.footballupdate.view.ui.DetailLeagueActivity.Companion.LEAGUE_NAME
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchResultActivity : AppCompatActivity(), SearchResultView {

    private lateinit var rvSearchResult: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: SearchResultAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var txtFailed: TextView
    private lateinit var txtQuery: TextView
    private lateinit var linearLayout: LinearLayout

    private var matches: MutableList<Match> = mutableListOf()

    companion object {
        const val QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout = verticalLayout {
            lparams(width = matchParent, height = matchParent)

            toolbar = themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                backgroundColor = ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
                title = resources.getString(R.string.app_name)
            }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize))

            txtQuery = textView {
                id = R.id.txt_query
                textSize = 16f
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(16)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            progressBar = progressBar {
                id = R.id.progress_bar_search
            }

            txtFailed = textView {
                text = resources.getString(R.string.no_data)
                id = R.id.txt_failed
                textSize = 20f
                visibility = View.GONE
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(16)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            rvSearchResult = recyclerView {
                id = R.id.rv_search_result
                layoutManager = LinearLayoutManager(context)
            }.lparams {
                width = matchParent
                height = wrapContent
            }

        }

        val intent = intent
        val query = intent.getStringExtra(QUERY)
        val leagueName = intent.getStringExtra(LEAGUE_NAME)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = SearchResultAdapter(matches) {
            this@SearchResultActivity.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId
            )
        }
        rvSearchResult.adapter = adapter

        presenter = SearchPresenter(this)

        if (leagueName == null) {
            presenter.doSearch(query)
        } else {
            presenter.doSearchInLeague(query, leagueName)
        }

        val textQuery = getString(R.string.search_result_for) + " '$query'"
        txtQuery.text = textQuery

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            hideLoading()
            txtFailed.text = message
            txtFailed.visible()

            linearLayout.snackbar(message).show()
        }
    }

    override fun showMatchList(data: List<Match>) {
        runOnUiThread {
            hideLoading()
            matches.clear()
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        }
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
                this@SearchResultActivity.startActivity<SearchResultActivity>(QUERY to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}
