package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.player.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showPlayerList(data: List<Player>)
}