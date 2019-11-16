package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.viewinterface.SearchResultView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(
    private val view: SearchResultView
) {
    fun doSearch(query: String?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.searchMatch(query)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (!it.isNullOrEmpty()) {
                            val filtered = it.filter { match ->
                                match.sportName == "Soccer"
                            }
                            if (filtered.isNotEmpty()) {
                                view.showMatchList(filtered)
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

    fun doSearchInLeague(query: String?, leagueName: String?) {
        view.showLoading()
        GlobalScope.launch {
            val result = MainApi().services.searchMatch(query)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (!it.isNullOrEmpty()) {
                            val filtered = it.filter { match ->
                                match.sportName == "Soccer"
                            }.filter { match ->
                                match.leagueName == leagueName
                            }
                            if (filtered.isNotEmpty()) {
                                view.showMatchList(filtered)
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

    fun doSearchTeam(query: String?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.searchTeam(query)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.teams.let {
                        if (!it.isNullOrEmpty()) {
                            val filtered = it.filter { teams ->
                                teams.teamSport == "Soccer"
                            }
                            if (filtered.isNotEmpty()) {
                                view.showTeamList(filtered)
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

    fun doSearchTeamInLeague(query: String?, leagueName: String?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.searchTeam(query)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.teams.let {
                        if (!it.isNullOrEmpty()) {
                            val filtered = it.filter { teams ->
                                teams.teamSport == "Soccer"
                            }.filter { team ->
                                team.teamLeague == leagueName
                            }

                            if (filtered.isNotEmpty()) {
                                view.showTeamList(filtered)
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