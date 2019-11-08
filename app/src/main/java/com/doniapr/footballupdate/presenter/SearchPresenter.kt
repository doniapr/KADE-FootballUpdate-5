package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.view.SearchResultView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(private val view: SearchResultView){
    fun doSearch(query: String?) {
        val matches: MutableList<Match> = mutableListOf()
        view.showLoading()
        doAsync {
            MainApi().services.searchMatch(query).enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    uiThread {
                        view.hideLoading()
                        view.onFailed(DetailLeaguePresenter.noInternetAccess)
                    }
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.matches != null) {
                            for (i in response.body()!!.matches.indices){
                                if (response.body()!!.matches[i].sportName == "Soccer"){
                                    matches.add(response.body()!!.matches[i])
                                }
                            }
                            if (matches.isNotEmpty()){
                                uiThread {
                                    view.hideLoading()
                                    view.showMatchList(matches)
                                }
                            }else{
                                uiThread {
                                    view.hideLoading()
                                    view.onFailed(DetailLeaguePresenter.noDataFound)
                                }
                            }
                        } else {
                            uiThread {
                                view.hideLoading()
                                view.onFailed(DetailLeaguePresenter.noDataFound)
                            }
                        }

                    }
                }
            })
        }
    }

    fun doSearchInLeague(query: String?, leagueName: String?) {
        val matches: MutableList<Match> = mutableListOf()
        view.showLoading()
        doAsync {
            MainApi().services.searchMatch(query).enqueue(object :
                Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    uiThread {
                        view.hideLoading()
                        view.onFailed(DetailLeaguePresenter.noInternetAccess)
                    }
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.matches != null) {
                            for (i in response.body()!!.matches.indices){
                                if (response.body()!!.matches[i].sportName == "Soccer"){
                                    if (response.body()!!.matches[i].leagueName == leagueName){
                                        matches.add(response.body()!!.matches[i])
                                    }
                                }
                            }
                            if (matches.isNotEmpty()){
                                uiThread {
                                    view.hideLoading()
                                    view.showMatchList(matches)
                                }
                            }else{
                                uiThread {
                                    view.hideLoading()
                                    view.onFailed(DetailLeaguePresenter.noDataFound)
                                }
                            }
                        } else {
                            uiThread {
                                view.hideLoading()
                                view.onFailed(DetailLeaguePresenter.noDataFound)
                            }
                        }

                    }
                }
            })
        }
    }
}