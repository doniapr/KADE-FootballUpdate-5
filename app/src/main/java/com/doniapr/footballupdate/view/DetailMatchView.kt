package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(message: String?)
    fun showMatchDetail(data: Match)
    fun showTeam(data: Team, isHome: Boolean)
}