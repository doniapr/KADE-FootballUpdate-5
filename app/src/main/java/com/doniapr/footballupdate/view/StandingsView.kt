package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Standings

interface StandingsView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showStandingList(data: List<Standings>)
}