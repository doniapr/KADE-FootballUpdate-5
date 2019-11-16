package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.match.Match

interface ListMatchView {
    fun showLoading(isLastMatch: Boolean)
    fun hideLoading(isLastMatch: Boolean)
    fun onFailed(type: Int, isLastMatch: Boolean)
    fun showLastMatch(data: List<Match>)
    fun showNextMatch(data: List<Match>)
}