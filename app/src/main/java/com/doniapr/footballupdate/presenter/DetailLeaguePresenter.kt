package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.view.DetailLeagueView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLeaguePresenter(
    private val view: DetailLeagueView
) {
    companion object{
        const val noDataFound = "Data pertandingan tidak Ditemukan"
        const val noInternetAccess = "Mohon periksa koneksi internet anda"
    }

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
}