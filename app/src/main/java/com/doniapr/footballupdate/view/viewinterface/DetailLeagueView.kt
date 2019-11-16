package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.league.LeagueDetail

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(message: String?)
    fun showLeagueDetail(data: List<LeagueDetail>?)
}