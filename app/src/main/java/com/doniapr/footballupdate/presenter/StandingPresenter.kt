package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.StandingsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter(private val view: StandingsView) {

    fun getStanding(leagueId: String) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getStanding(leagueId, "1920")
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.standing.let {
                        if (!it.isNullOrEmpty()) {
                            if (it.isNotEmpty()) {
                                view.showStandingList(it)
                            } else {
                                view.onFailed(1)
                            }
                        } else {
                            view.onFailed(1)
                        }
                    }
                } else {
                    view.onFailed(2)
                }
            }
        }
    }

}