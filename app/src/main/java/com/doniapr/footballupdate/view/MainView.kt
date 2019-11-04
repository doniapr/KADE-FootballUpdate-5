package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(message: String?)
    fun showLeagueDetail(data: List<LeagueDetail>?)
    fun showMatchList(data: List<Match>)
    fun showMatchDetail(data: Match)
    fun showTeam(data: Team, isHome: Boolean)
}