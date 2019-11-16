package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.viewinterface.ListMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(
    private val view: ListMatchView
) {

    fun getLastMatch(leagueId: Int?) {
        view.showLoading(true)

        GlobalScope.launch {
            val result = MainApi().services.getLastMatch(leagueId.toString())
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (!it.isNullOrEmpty()) {
                            view.showLastMatch(it)
                        } else {
                            view.onFailed(1, true)
                        }
                    }
                } else {
                    view.onFailed(1, true)
                }
            } else {
                view.onFailed(2, true)
            }

        }
    }

    fun getNextMatch(leagueId: Int?) {
        view.showLoading(false)

        GlobalScope.launch {
            val result = MainApi().services.getNextMatch(leagueId.toString())
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (!it.isNullOrEmpty()) {
                            view.showNextMatch(it)
                        } else {
                            view.onFailed(1, false)
                        }
                    }
                } else {
                    view.onFailed(1, false)
                }
            } else {
                view.onFailed(2, false)
            }
        }
    }
}