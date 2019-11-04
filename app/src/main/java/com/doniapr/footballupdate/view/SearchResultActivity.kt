package com.doniapr.footballupdate.view

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
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.MainPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchResultActivity : AppCompatActivity(), MainView {

    private lateinit var rvSearchResult: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: SearchResultAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var txtFailed: TextView
    private lateinit var txtQuery: TextView
    private lateinit var linearLayout: LinearLayout

    private var matches: MutableList<Match> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout = verticalLayout {
            lparams(width = matchParent, height = matchParent)

            toolbar = toolbar {
                backgroundColor = ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
                title = resources.getString(R.string.search_result)
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            txtQuery = textView {
                textSize = 16f
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(16)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            progressBar = progressBar()

            txtFailed = textView {
                text = resources.getString(R.string.no_data)
                textSize = 20f
                visibility = View.GONE
            }.lparams {
                width = matchParent
                height = wrapContent
                margin = dip(16)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            rvSearchResult = recyclerView {
                layoutManager = LinearLayoutManager(context)
            }.lparams {
                width = matchParent
                height = wrapContent
            }

        }

        val intent = intent
        val query = intent.getStringExtra("query")

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = SearchResultAdapter(matches)
        rvSearchResult.adapter = adapter

        presenter = MainPresenter(this)
        presenter.doSearch(query)
        val textQuery = "Hasil pencarian untuk '$query'"
        txtQuery.text = textQuery

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onFailed(message: String?) {
        txtFailed.text = message
        txtFailed.visible()

        Snackbar.make(linearLayout, message.toString(), Snackbar.LENGTH_SHORT)

    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {

    }

    override fun showMatchList(data: List<Match>) {
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showMatchDetail(data: Match) {
    }

    override fun showTeam(data: Team, isHome: Boolean) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                this@SearchResultActivity.startActivity<SearchResultActivity>("query" to query)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
}
