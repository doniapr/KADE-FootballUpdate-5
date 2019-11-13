package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.DetailLeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(
    private val view: DetailLeagueView
) {

    fun getLeagueDetail(leagueId: String?) {

        GlobalScope.launch {
            view.showLoading()
            val result = MainApi().services.getDetailLeague(leagueId.toString())
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.league.let {
                        view.showLeagueDetail(it)
                    }
                } else {
                    view.onFailed(result.message())
                }
            } else {
                view.onFailed(result.message())
            }
        }
    }
}