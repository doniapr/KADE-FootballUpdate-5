package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(
    private val view: TeamView
) {
    fun getTeams(leagueId: Int?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getTeam(leagueId.toString(), "1920")
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.teams.let {
                        if (!it.isNullOrEmpty()) {
                            view.showTeamList(it)
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
}