package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match

interface ListMatchView {
    fun showLoading(isLastMatch: Boolean)
    fun hideLoading(isLastMatch: Boolean)
    fun onFailed(type: Int, isLastMatch: Boolean)
    fun showLastMatch(data: List<Match>)
    fun showNextMatch(data: List<Match>)
}