package com.doniapr.footballupdate.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.DetailLeaguePagerAdapter
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.MainPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import org.jetbrains.anko.startActivity

class DetailLeagueActivity : AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter

    companion object {
        const val LEAGUE_ID = "league_id"
        const val LEAGUE_NAME = "league_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val intent = intent
        val leagueId = intent.getIntExtra(LEAGUE_ID, 0)
        val leagueName = intent.getStringExtra(LEAGUE_NAME)
        toolbar_detail_league.title = leagueName

        setSupportActionBar(toolbar_detail_league)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MainPresenter(this)
        presenter.getLeagueDetail(leagueId.toString())

        val detailLeaguePagerAdapter =
            DetailLeaguePagerAdapter(this, supportFragmentManager, leagueId)
        viewpager_detail_league.adapter = detailLeaguePagerAdapter
        tab_layout_detail_league.setupWithViewPager(viewpager_detail_league)

    }

    override fun showLoading() {
        cv_detail_league.invisible()
        progress_bar_detail_league.visible()
    }

    override fun hideLoading() {
        cv_detail_league.visible()
        progress_bar_detail_league.invisible()
    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {
        Picasso.get().load(data?.get(0)?.leagueBadge).into(img_league_badge)
        txt_league_name.text = data?.get(0)?.leagueName

        if (data?.get(0)?.leagueWebsite != null || data?.get(0)?.leagueWebsite != "") {
            btn_league_web.setOnClickListener {
                var urlWeb = data!![0].leagueWebsite
                if (!urlWeb!!.startsWith("http://") || !urlWeb.startsWith("https://")) {
                    urlWeb = "http://$urlWeb"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWeb))
                    this@DetailLeagueActivity.startActivity(intent)
                }
            }
        } else {
            btn_league_web.invisible()
        }

        if (data?.get(0)?.leagueFacebook != null || data?.get(0)?.leagueFacebook != "") {
            btn_league_facebook.setOnClickListener {
                var urlFb = data!![0].leagueFacebook
                if (!urlFb!!.startsWith("http://") || !urlFb.startsWith("https://")) {
                    urlFb = "http://$urlFb"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFb))
                    this@DetailLeagueActivity.startActivity(intent)
                }
            }
        } else {
            btn_league_facebook.invisible()
        }

        if (data?.get(0)?.leagueTwitter != null || data?.get(0)?.leagueTwitter != "") {
            btn_league_twitter.setOnClickListener {
                var urlTwitter = data!![0].leagueTwitter
                if (!urlTwitter!!.startsWith("http://") || !urlTwitter.startsWith("https://")) {
                    urlTwitter = "http://$urlTwitter"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlTwitter))
                    this@DetailLeagueActivity.startActivity(intent)
                }
            }
        } else {
            btn_league_twitter.invisible()
        }

        if (data?.get(0)?.leagueYoutube != null || data?.get(0)?.leagueYoutube != "") {
            btn_league_youtube.setOnClickListener {
                var urlYt = data!![0].leagueYoutube
                if (!urlYt!!.startsWith("http://") || !urlYt.startsWith("https://")) {
                    urlYt = "http://$urlYt"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlYt))
                    this@DetailLeagueActivity.startActivity(intent)
                }
            }
        } else {
            btn_league_youtube.invisible()
        }
    }

    override fun showMatchList(data: List<Match>) {}

    override fun onFailed(message: String?) {

    }

    override fun showMatchDetail(data: Match) {}

    override fun showTeam(data: Team, isHome: Boolean) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                this@DetailLeagueActivity.startActivity<SearchResultActivity>("query" to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

}
