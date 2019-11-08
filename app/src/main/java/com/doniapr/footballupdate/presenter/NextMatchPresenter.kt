package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.view.NextMatchView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextMatchPresenter (private val view: NextMatchView){
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
                            if (response.body()?.matches != null) {
                                view.hideLoading()
                                view.showMatchList(response.body()!!.matches)
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
}