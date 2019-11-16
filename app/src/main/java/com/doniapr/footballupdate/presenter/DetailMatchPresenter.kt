package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.viewinterface.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(
    private val view: DetailMatchView
) {
    fun getMatchDetail(eventId: String) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getMatchDetail(eventId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches?.get(0).let {
                        if (it != null) {
                            view.showMatchDetail(it)
                        } else {
                            view.onFailed(1)
                        }
                    }
                } else {
                    view.onFailed(1)
                }
            } else {
                view.onFailed(2)
            }
        }
    }

    fun getTeamInfo(teamId: String, isHome: Boolean) {
        GlobalScope.launch {
            val result = MainApi().services.getTeamInfo(teamId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.teams?.get(0).let {
                        if (it != null) {
                            view.showTeam(it, isHome)
                        } else {
                            view.onFailed(3)
                        }
                    }
                } else {
                    view.onFailed(3)
                }
            } else {
                view.onFailed(2)
            }
        }
    }
}