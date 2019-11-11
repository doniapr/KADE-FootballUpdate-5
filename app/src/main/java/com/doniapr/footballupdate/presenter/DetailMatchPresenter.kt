package com.doniapr.footballupdate.presenter

import android.content.Context
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.TeamResponse
import com.doniapr.footballupdate.view.DetailMatchView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val context: Context
) {
    fun getMatchDetail(eventId: String) {
        view.showLoading()
        doAsync {
            MainApi().services.getMatchDetail(eventId).enqueue(object :
                Callback<MatchResponse> {
                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    view.hideLoading()
                    view.onFailed(context.getString(R.string.no_internet))
                }

                override fun onResponse(
                    call: Call<MatchResponse>,
                    response: Response<MatchResponse>
                ) {
                    if (response.code() == 200) {
                        response.body()?.matches?.get(0).let {
                            if (it != null) {
                                view.hideLoading()
                                view.showMatchDetail(it)
                            } else {
                                view.hideLoading()
                                view.onFailed(context.getString(R.string.no_data))
                            }
                        }
                    } else {
                        view.hideLoading()
                        view.onFailed(context.getString(R.string.no_data))
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
                    view.onFailed(context.getString(R.string.failed_load_team_badge))
                }

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {
                    if (response.code() == 200) {
                        response.body()?.teams?.get(0).let {
                            if (it != null) {
                                view.showTeam(it, isHome)
                            } else {
                                view.onFailed(context.getString(R.string.failed_load_team_badge))
                            }
                        }
                    }
                }

            })
        }
    }
}