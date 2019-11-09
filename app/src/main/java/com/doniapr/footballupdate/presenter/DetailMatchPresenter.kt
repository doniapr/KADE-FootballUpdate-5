package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.TeamResponse
import com.doniapr.footballupdate.view.DetailMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchPresenter (private val view: DetailMatchView) {
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
                            if (response.body()?.matches?.get(0) != null) {
                                view.hideLoading()
                                view.showMatchDetail(response.body()!!.matches[0])
                            } else {
                                view.hideLoading()
                                view.onFailed(DetailLeaguePresenter.noDataFound)
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
                    uiThread {
                        view.onFailed(t.message)
                    }
                }

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    if (response.code() == 200) {
                        uiThread {
                            if (response.body()?.teams?.get(0) != null) {
                                view.showTeam(response.body()!!.teams[0], isHome)
                            } else {
                                view.onFailed("Failed to load team badge")
                            }
                        }
                    }
                }

            })
        }
    }
}