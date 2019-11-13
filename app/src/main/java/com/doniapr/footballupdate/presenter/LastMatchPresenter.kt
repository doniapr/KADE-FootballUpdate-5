package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.LastMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LastMatchPresenter(
    private val view: LastMatchView
) {

    fun getLastMatch(leagueId: Int?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getLastMatch(leagueId.toString())
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