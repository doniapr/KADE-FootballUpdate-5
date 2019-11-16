package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showPlayerList(data: List<Player>)
}