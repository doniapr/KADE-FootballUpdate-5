package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.viewinterface.DetailTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter(private val view: DetailTeamView) {

    fun getTeamInfo(teamId: String) {
        view.showLoading(1)

        GlobalScope.launch {
            val result = MainApi().services.getTeamInfo(teamId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.teams?.get(0).let {
                        if (it != null) {
                            view.showTeamDetail(it)
                            view.hideLoading(1)
                        } else {
                            view.onFailed(1)
                            view.hideLoading(1)
                        }
                    }
                } else {
                    view.onFailed(2)
                    view.hideLoading(1)
                }
            }
        }
    }

    fun getLastMatch(teamId: String) {
        view.showLoading(2)
        GlobalScope.launch {
            val result = MainApi().services.getTeamLastMatch(teamId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (it != null) {
                            view.showLastMatch(it)
                        } else {
                            view.hideLoading(2)
                        }
                    }
                } else {
                    view.hideLoading(2)
                }
            }
        }
    }

    fun getNextMatch(teamId: String) {
        view.showLoading(3)
        GlobalScope.launch {
            val result = MainApi().services.getTeamNextMatch(teamId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.matches.let {
                        if (it != null) {
                            view.showNextMatch(it)
                        } else {
                            view.hideLoading(3)
                        }
                    }
                } else {
                    view.hideLoading(3)
                }
            }
        }
    }

}