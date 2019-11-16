package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.model.FavoriteMatch
import com.doniapr.footballupdate.model.FavoriteTeam

interface FavoriteView {
    fun onFailed()
    fun showFavorite(data: List<FavoriteMatch>)
    fun showFavoriteTeam(data: List<FavoriteTeam>)
}