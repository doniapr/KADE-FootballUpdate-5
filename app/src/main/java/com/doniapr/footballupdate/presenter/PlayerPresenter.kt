package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.view.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView
) {
    fun getTeamPlayers(teamId: Int?) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getTeamPlayer(teamId.toString())
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.player.let {
                        if (!it.isNullOrEmpty()) {
                            view.showPlayerList(it)
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

    fun getPlayersDetail(playerId: Int) {
        view.showLoading()

        GlobalScope.launch {
            val result = MainApi().services.getPlayerDetail(playerId)
            if (result.isSuccessful) {
                if (result.code() == 200) {
                    result.body()?.player.let {
                        if (!it.isNullOrEmpty()) {
                            view.showPlayerList(it)
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