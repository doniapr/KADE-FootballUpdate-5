package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showTeamList(data: List<Team>)
}