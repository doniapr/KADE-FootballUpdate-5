package com.doniapr.footballupdate.presenter

import android.content.Context
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.view.SearchResultView
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(
    private val view: SearchResultView,
    private val context: Context
) {
    fun doSearch(query: String?) {
        view.showLoading()
        doAsync {
            MainApi().services.searchMatch(query).enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    view.hideLoading()
                    view.onFailed(context.getString(R.string.no_internet))

                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.code() == 200) {
                        response.body()?.matches.let {
                            if (!it.isNullOrEmpty()) {
                                val filtered = it.filter { match ->
                                    match.sportName == context.getString(R.string.soccer)
                                }
                                if (filtered.isNotEmpty()) {
                                    view.hideLoading()
                                    view.showMatchList(filtered)
                                } else {
                                    view.hideLoading()
                                    view.onFailed(context.getString(R.string.no_data))
                                }
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

    fun doSearchInLeague(query: String?, leagueName: String?) {
        view.showLoading()
        doAsync {
            MainApi().services.searchMatch(query).enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    view.hideLoading()
                    view.onFailed(context.getString(R.string.no_internet))
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.code() == 200) {
                        response.body()?.matches.let {
                            if (!it.isNullOrEmpty()) {
                                val filtered = response.body()!!.matches.filter { match ->
                                    match.sportName == context.getString(R.string.soccer)
                                }.filter { match ->
                                    match.leagueName == leagueName
                                }

                                if (filtered.isNotEmpty()) {
                                    view.hideLoading()
                                    view.showMatchList(filtered)
                                } else {
                                    view.hideLoading()
                                    view.onFailed(context.getString(R.string.no_data))
                                }
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
}