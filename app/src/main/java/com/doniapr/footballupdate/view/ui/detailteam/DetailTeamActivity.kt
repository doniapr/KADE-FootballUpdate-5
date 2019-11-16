package com.doniapr.footballupdate.view.ui.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.pageradapter.DetailTeamPagerAdapter
import com.doniapr.footballupdate.database.database
import com.doniapr.footballupdate.model.favorite.FavoriteTeam
import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.team.Team
import com.doniapr.footballupdate.presenter.DetailTeamPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.viewinterface.DetailTeamView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(),
    DetailTeamView {

    private lateinit var presenter: DetailTeamPresenter
    private lateinit var team: Team
    private var teamName: String? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var teamId: Int = 0


    companion object {
        const val TEAM_DETAIL_ID = "team_id"
        const val TEAM_DETAIL_NAME = "team_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val intent = intent
        teamId = intent.getIntExtra(TEAM_DETAIL_ID, 0)
        teamName = intent.getStringExtra(TEAM_DETAIL_NAME)
        toolbar_detail_team.title = teamName
        txt_team_name_detail.text = teamName

        setSupportActionBar(toolbar_detail_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = DetailTeamPresenter(this)
        presenter.getTeamInfo(teamId.toString())

        favoriteState()

        val detailTeamPagerAdapter =
            DetailTeamPagerAdapter(
                this,
                supportFragmentManager,
                teamId
            )
        viewpager_detail_team.adapter = detailTeamPagerAdapter
        tab_layout_detail_team.setupWithViewPager(viewpager_detail_team)
    }

    override fun showLoading(type: Int) {
        progress_bar_detail_team.visible()
        cv_detail_team.invisible()
    }

    override fun hideLoading(type: Int) {
        runOnUiThread {
            progress_bar_detail_team.invisible()
            cv_detail_team.visible()
        }
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            hideLoading(1)
            this@DetailTeamActivity.toast(message).show()
        }
    }

    override fun showTeamDetail(data: Team) {
        runOnUiThread {
            team = data
            hideLoading(1)
            if (!data.teamStadiumThumb.isNullOrEmpty()) {
                Picasso.get().load(data.teamStadiumThumb).into(img_banner_team_detail)
            }
            Picasso.get().load(data.teamBadge).into(img_team_badge_detail)
            txt_team_name_detail.text = data.teamName
            txt_team_stadium.text = data.teamStadium
            txt_league_name.text = data.teamLeague
        }
    }

    override fun showLastMatch(data: List<Match>) {}
    override fun showNextMatch(data: List<Match>) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_favorite -> {
                if (this::team.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                } else {
                    container_detail_team.snackbar(getString(R.string.data_not_ready))
                }

                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_added_24dp)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to team.teamId.toString(),
                    FavoriteTeam.TEAM_NAME to team.teamName,
                    FavoriteTeam.TEAM_BADGE to team.teamBadge,
                    FavoriteTeam.TEAM_LEAGUE to team.teamLeague
                )
            }
            container_detail_team.snackbar(getString(R.string.favorite_added)).show()
        } catch (e: SQLiteConstraintException) {
            container_detail_team.snackbar(e.message.toString()).show()
        }
    }


    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(${FavoriteTeam.TEAM_ID} = {id})",
                    "id" to team.teamId.toString()
                )
            }
            container_detail_team.snackbar(getString(R.string.favorite_removed)).show()
        } catch (e: SQLiteConstraintException) {
            container_detail_team.snackbar(e.message.toString()).show()
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(
                    "(${FavoriteTeam.TEAM_ID} = {id})",
                    "id" to teamId
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }
}
