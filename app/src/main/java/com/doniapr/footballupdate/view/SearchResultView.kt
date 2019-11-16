package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team

interface SearchResultView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showMatchList(data: List<Match>)
    fun showTeamList(data: List<Team>)
}