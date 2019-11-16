package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.team.Team

interface DetailTeamView {
    fun showLoading(type: Int)
    fun hideLoading(type: Int)
    fun onFailed(type: Int)
    fun showTeamDetail(data: Team)
    fun showNextMatch(data: List<Match>)
    fun showLastMatch(data: List<Match>)

}