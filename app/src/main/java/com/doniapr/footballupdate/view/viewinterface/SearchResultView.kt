package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.team.Team

interface SearchResultView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showMatchList(data: List<Match>)
    fun showTeamList(data: List<Team>)
}