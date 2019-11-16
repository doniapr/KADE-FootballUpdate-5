package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team

interface DetailTeamView {
    fun showLoading(type: Int)
    fun hideLoading(type: Int)
    fun onFailed(type: Int)
    fun showTeamDetail(data: Team)
    fun showNextMatch(data: List<Match>)
    fun showLastMatch(data: List<Match>)

}