package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.Match

interface SearchResultView {
    fun showLoading()
    fun hideLoading()
    fun onFailed(type: Int)
    fun showMatchList(data: List<Match>)
}