package com.doniapr.footballupdate.view

import com.doniapr.footballupdate.favorite.Favorite

interface FavoriteView {
    fun onFailed()
    fun showFavorite(data: List<Favorite>)
}