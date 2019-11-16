package com.doniapr.footballupdate.view.viewinterface

import com.doniapr.footballupdate.model.favorite.FavoriteMatch
import com.doniapr.footballupdate.model.favorite.FavoriteTeam

interface FavoriteView {
    fun onFailed()
    fun showFavorite(data: List<FavoriteMatch>)
    fun showFavoriteTeam(data: List<FavoriteTeam>)
}