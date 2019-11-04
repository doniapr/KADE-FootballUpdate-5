package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiService.MainApi
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.model.TeamResponse
import com.doniapr.footballupdate.view.MainView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(
    private val view: MainView
) {
    val noDataFound = "Data pertandingan tidak Ditemukan"
    val noInternetAccess = "Mohon periksa koneksi internet anda"

    fun getLeagueDetail(leagueId: String?) {
        view.showLoading()
        doAsync {
            MainApi().services.getDetailLeague(leagueId.toString()).enqueue(object :
                Callback<LeagueDetailResponse> {
                override fun onResponse(
                    call: Call<LeagueDetailResponse>,
                    response: Response<LeagueDetailResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            view.hideLoading()
                            view.showLeagueDetail(response.body()?.league)
                        }
                    }
                }

                override fun onFailure(call: Call<LeagueDetailResponse>, t: Throwable) {
                    view.hideLoading()
                }
            })
        }
    }

    fun getLastMatch(leagueId: Int?) {
        view.showLoading()
        doAsync {
            MainApi().services.getLastMatch(leagueId.toString()).enqueue(object :
                Callback<MatchResponse> {
                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    uiThread {
                        view.onFailed(t.message)
                        view.hideLoading()
                    }
                }

                override fun onResponse(
                    call: Call<MatchResponse>,
                    response: Response<MatchResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            if (response.body()?.matchs != null) {
                                view.hideLoading()
                                view.showMatchList(response.body()!!.matchs)
                            } else {
                                view.hideLoading()
                                view.onFailed(noDataFound)
                            }
                        }
                    } else {
                        uiThread {
                            view.onFailed(response.message())
                            view.hideLoading()
                        }
                    }
                }

            })
        }

    }

    fun getNextMatch(leagueId: Int?) {
        view.showLoading()
        doAsync {
            MainApi().services.getNextMatch(leagueId.toString()).enqueue(object :
                Callback<MatchResponse> {
                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    uiThread {
                        view.onFailed(t.message)
                        view.hideLoading()
                    }
                }

                override fun onResponse(
                    call: Call<MatchResponse>,
                    response: Response<MatchResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            if (response.body()?.matchs != null) {
                                view.hideLoading()
                                view.showMatchList(response.body()!!.matchs)
                            } else {
                                view.hideLoading()
                                view.onFailed(noDataFound)
                            }
                        }
                    } else {
                        uiThread {
                            view.hideLoading()
                            view.onFailed(response.message())
                        }
                    }
                }

            })
        }
    }

    fun doSearch(query: String?) {
        view.showLoading()
        doAsync {
            MainApi().services.searchMatch(query).enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    uiThread {
                        view.hideLoading()
                        view.onFailed(noInternetAccess)
                    }
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.matches != null) {
                            uiThread {
                                view.hideLoading()
                                view.showMatchList(response.body()!!.matches)
                            }
                        } else {
                            uiThread {
                                view.hideLoading()
                                view.onFailed(noDataFound)
                            }
                        }

                    }
                }
            })
        }
    }

    fun getMatchDetail(eventId: String) {
        view.showLoading()
        doAsync {
            MainApi().services.getMatchDetail(eventId).enqueue(object :
                Callback<MatchResponse> {
                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    uiThread {
                        view.hideLoading()
                        view.onFailed(t.message)
                    }
                }

                override fun onResponse(
                    call: Call<MatchResponse>,
                    response: Response<MatchResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            if (response.body()?.matchs?.get(0) != null) {
                                view.hideLoading()
                                view.showMatchDetail(response.body()!!.matchs[0])
                            } else {
                                view.hideLoading()
                                view.onFailed(noDataFound)
                            }
                        }
                    } else {
                        uiThread {
                            view.hideLoading()
                            view.onFailed(response.message())
                        }
                    }
                }

            })
        }
    }

    fun getTeamInfo(teamId: String, isHome: Boolean) {
        doAsync {
            MainApi().services.getTeamInfo(teamId).enqueue(object :
                Callback<TeamResponse> {
                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            if (response.body()?.teams?.get(0) != null) {
                                view.showTeam(response.body()!!.teams[0], isHome)
                            }
                        }
                    }
                }

            })
        }
    }
}