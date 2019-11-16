package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.standing.Standings

interface StandingsView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showStandingList(data: List<Standings>)
}