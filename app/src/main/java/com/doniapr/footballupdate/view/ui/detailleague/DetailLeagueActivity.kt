package com.doniapr.footballupdate.view.ui.detailleague

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.pageradapter.DetailLeaguePagerAdapter
import com.doniapr.footballupdate.model.league.LeagueDetail
import com.doniapr.footballupdate.presenter.DetailLeaguePresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ui.search.SearchResultActivity
import com.doniapr.footballupdate.view.ui.search.SearchResultActivity.Companion.QUERY
import com.doniapr.footballupdate.view.viewinterface.DetailLeagueView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import org.jetbrains.anko.startActivity

class DetailLeagueActivity : AppCompatActivity(),
    DetailLeagueView {
    private lateinit var presenter: DetailLeaguePresenter
    private var leagueName: String? = null

    companion object {
        const val LEAGUE_ID = "league_id"
        const val LEAGUE_NAME = "league_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val intent = intent
        val leagueId = intent.getIntExtra(LEAGUE_ID, 0)
        leagueName = intent.getStringExtra(LEAGUE_NAME)
        toolbar_detail_league.title = leagueName
        txt_league_name.text = leagueName

        setSupportActionBar(toolbar_detail_league)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = DetailLeaguePresenter(this)
        presenter.getLeagueDetail(leagueId.toString())

        val detailLeaguePagerAdapter =
            DetailLeaguePagerAdapter(
                this,
                supportFragmentManager,
                leagueId
            )
        viewpager_detail_league.adapter = detailLeaguePagerAdapter
        tab_layout_detail_league.setupWithViewPager(viewpager_detail_league)

    }

    override fun showLoading() {
        runOnUiThread {
            cv_detail_league.invisible()
            progress_bar_detail_league.visible()
        }
    }

    override fun hideLoading() {
        cv_detail_league.visible()
        progress_bar_detail_league.invisible()
    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {
        runOnUiThread {
            Picasso.get().load(data?.get(0)?.leagueBadge).into(img_league_badge)
            txt_league_name.text = data?.get(0)?.leagueName
            leagueName = data?.get(0)?.leagueName.toString()
            txt_league_country.text = data?.get(0)?.leagueCountry

            var urlWeb = data?.get(0)?.leagueWebsite
            var urlFb = data?.get(0)?.leagueFacebook
            var urlTwitter = data?.get(0)?.leagueTwitter
            var urlYt = data?.get(0)?.leagueYoutube

            if (!urlWeb.isNullOrEmpty()) {
                btn_league_web.setOnClickListener {
                    if (!urlWeb?.startsWith("http://")!! || !urlWeb?.startsWith("https://")!!) {
                        urlWeb = "http://$urlWeb"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb))
                        this@DetailLeagueActivity.startActivity(intent)
                    }
                }
            } else {
                btn_league_web.invisible()
            }

            if (!urlFb.isNullOrEmpty()) {
                btn_league_facebook.setOnClickListener {
                    if (!urlFb?.startsWith("http://")!! || !urlFb?.startsWith("https://")!!) {
                        urlFb = "http://$urlFb"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFb))
                        this@DetailLeagueActivity.startActivity(intent)
                    }
                }
            } else {
                btn_league_facebook.invisible()
            }

            if (!urlTwitter.isNullOrEmpty()) {
                btn_league_twitter.setOnClickListener {
                    if (!urlTwitter?.startsWith("http://")!! || !urlTwitter?.startsWith("https://")!!) {
                        urlTwitter = "http://$urlTwitter"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlTwitter))
                        this@DetailLeagueActivity.startActivity(intent)
                    }
                }
            } else {
                btn_league_twitter.invisible()
            }

            if (!urlYt.isNullOrEmpty()) {
                btn_league_youtube.setOnClickListener {
                    if (!urlYt?.startsWith("http://")!! || !urlYt?.startsWith("https://")!!) {
                        urlYt = "http://$urlYt"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlYt))
                        this@DetailLeagueActivity.startActivity(intent)
                    }
                }
            } else {
                btn_league_youtube.invisible()
            }
            hideLoading()
        }
    }

    override fun onFailed(message: String?) {
        runOnUiThread {
            cv_detail_league.visible()
            btn_league_web.invisible()
            btn_league_facebook.invisible()
            btn_league_twitter.invisible()
            btn_league_youtube.invisible()
            hideLoading()
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
                this@DetailLeagueActivity.startActivity<SearchResultActivity>(
                    QUERY to query,
                    LEAGUE_NAME to leagueName
                )
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

}
