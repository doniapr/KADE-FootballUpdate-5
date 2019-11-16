package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.team.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showMatchDetail(data: Match)
    fun showTeam(data: Team, isHome: Boolean)
}