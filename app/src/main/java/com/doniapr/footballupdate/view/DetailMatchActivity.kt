package com.doniapr.footballupdate.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.MainPresenter
import com.doniapr.footballupdate.utility.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*

class DetailMatchActivity : AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter
    private lateinit var match: Match
    private lateinit var team: Team

    companion object {
        const val EVENT_ID: String = "event_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        setSupportActionBar(toolbar_detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val eventId = intent.getIntExtra(EVENT_ID, 0)

        presenter = MainPresenter(this)
        presenter.getMatchDetail(eventId.toString())
    }

    override fun showLoading() {
        progress_bar_detail_match.visible()
    }

    override fun hideLoading() {
        progress_bar_detail_match.invisible()
    }

    override fun onFailed(message: String?) {
        Toast.makeText(this@DetailMatchActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {
    }

    override fun showMatchList(data: List<Match>) {

    }

    override fun showMatchDetail(data: Match) {
        supportActionBar?.title = data.eventName
        match = data

        Picasso.get().load(data.matchBanner + "/preview").into(img_banner_match)
        // Set Home
        txt_match_home.text = data.homeTeam
        txt_lineup_home.text = data.homeTeam
        // Match Result
        val round = data.leagueName + " " + resources.getString(R.string.round) + " " + data.round
        txt_match_round.text = round
        setDate(data)
        if (data.homeScore != null) {
            txt_match_home_score.text = data.homeScore.toString()
            txt_stats_home_goal.text = data.homeScore.toString()
        }
        if (data.homeGoalDetail != null || data.homeGoalDetail != "") {
            txt_home_goal_scorer.text = data.homeGoalDetail
        }
        //Stats
        if (data.homeShot != null) {
            txt_stats_home_shots.text = data.homeShot
        }
        if (data.homeRedCard != null || data.homeRedCard != "") {
            txt_stats_home_red_card.text = data.homeRedCard
        }
        if (data.homeYellowCard != null || data.homeYellowCard != "") {
            txt_stats_home_yellow_card.text = data.homeYellowCard
        }
        //Lineup
        if (data.homeGK != null || data.homeGK != "") {
            txt_lineup_gk_home.text = data.homeGK
        }
        if (data.homeDF != null || data.homeDF != "") {
            txt_lineup_df_home.text = data.homeDF
        }
        if (data.homeMF != null || data.homeMF != "") {
            txt_lineup_mf_home.text = data.homeMF
        }
        if (data.homeCF != null || data.homeCF != "") {
            txt_lineup_cf_home.text = data.homeCF
        }
        if (data.homeSubtitute != null || data.homeSubtitute != "") {
            txt_lineup_subs_home.text = data.homeSubtitute
        }

        // Set Away
        txt_match_away.text = data.awayTeam
        txt_lineup_away.text = data.awayTeam
        // Match Result
        if (data.awayScore != null) {
            txt_match_away_score.text = data.awayScore.toString()
            txt_stats_away_goal.text = data.awayScore.toString()
        }
        if (data.awayGoalDetail != null || data.awayGoalDetail != "") {
            txt_away_goal_scorer.text = data.awayGoalDetail
        }
        //Stats
        if (data.awayShot != null) {
            txt_stats_away_shots.text = data.awayShot
        }
        if (data.awayRedCard != null || data.awayRedCard != "") {
            txt_stats_away_red_card.text = data.awayRedCard
        }
        if (data.awayYellowCard != null || data.awayYellowCard != "") {
            txt_stats_away_yellow_card.text = data.awayYellowCard
        }
        //Lineup
        if (data.awayGK != null || data.awayGK != "") {
            txt_lineup_gk_away.text = data.awayGK
        }
        if (data.awayDF != null || data.awayDF != "") {
            txt_lineup_df_away.text = data.awayDF
        }
        if (data.awayMF != null || data.awayMF != "") {
            txt_lineup_mf_away.text = data.awayMF
        }
        if (data.awayCF != null || data.awayCF != "") {
            txt_lineup_cf_away.text = data.awayCF
        }
        if (data.awaySubtitute != null || data.awaySubtitute != "") {
            txt_lineup_subs_away.text = data.awaySubtitute
        }

        presenter.getTeamInfo(data.idHomeTeam.toString(), true)
        presenter.getTeamInfo(data.idAwayTeam.toString(), false)
    }

    override fun showTeam(data: Team, isHome: Boolean) {
        team = data
        if (!data.teamBadge.isNullOrEmpty()) {
            if (isHome) {
                Picasso.get().load(data.teamBadge + "/preview").into(img_match_home_team_badge)
            } else {
                Picasso.get().load(data.teamBadge + "/preview").into(img_match_away_team_badge)
            }
        }
    }

    private fun setDate(match: Match) {
        if (!match.dateEvent.isNullOrEmpty() && !match.time.isNullOrEmpty()) {
            val utcDate = match.dateEvent.toString() + " " + match.time.toString()
            val wibDate = utcDate.toDateAndHour()
            val formatedDate = wibDate.formatTo("dd MMMM yyyy") + " " + wibDate.formatTo("HH:mm:ss")
            txt_match_detail_date.text = formatedDate
        } else if (!match.dateEvent.isNullOrEmpty() && match.time.isNullOrEmpty()) {
            val utcDate = match.dateEvent.toString()
            val wibDate = utcDate.toDate()
            txt_match_detail_date.text = wibDate.formatTo("dd MMMM yyyy")
        } else if (match.dateEvent.isNullOrEmpty() && !match.time.isNullOrEmpty()) {
            val utcDate = match.time.toString()
            val wibDate = utcDate.toHour()
            txt_match_detail_date.text = wibDate.formatTo("HH:mm:ss")

        } else {
            txt_match_detail_date.text = "-"
        }
    }
}
