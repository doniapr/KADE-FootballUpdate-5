package com.doniapr.footballupdate.presenter

import android.content.Context
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.view.DetailLeagueView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val context: Context
) {

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
                        view.hideLoading()
                        view.showLeagueDetail(response.body()?.league)
                    } else {
                        view.hideLoading()
                        view.onFailed(context.getString(R.string.no_data))
                    }
                }

                override fun onFailure(call: Call<LeagueDetailResponse>, t: Throwable) {
                    view.hideLoading()
                    view.onFailed(context.getString(R.string.no_internet))
                }
            })
        }
    }
}