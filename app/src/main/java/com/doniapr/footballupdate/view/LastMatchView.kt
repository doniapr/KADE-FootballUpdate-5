package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(message: String?)
    fun showMatchList(data: List<Match>)
}