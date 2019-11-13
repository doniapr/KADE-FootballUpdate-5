package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.NextMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(
    private val view: NextMatchView
) {
    fun getNextMatch(leagueId: Int?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getNextMatch(leagueId.toString())
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (!it.isNullOrEmpty()) {
                            view.showMatchList(it)
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