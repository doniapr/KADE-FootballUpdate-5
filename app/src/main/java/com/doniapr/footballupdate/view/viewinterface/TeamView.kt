package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.team.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showTeamList(data: List<Team>)
}