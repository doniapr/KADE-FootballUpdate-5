package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.SearchResultView
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
}