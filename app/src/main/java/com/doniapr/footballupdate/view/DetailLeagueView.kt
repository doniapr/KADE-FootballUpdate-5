package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.LeagueDetail

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(message: String?)
    fun showLeagueDetail(data: List<LeagueDetail>?)
}