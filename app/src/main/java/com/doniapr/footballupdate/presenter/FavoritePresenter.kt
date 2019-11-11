package com.doniapr.footballupdate.presenter

import android.content.Context
import com.doniapr.footballupdate.database.database
import com.doniapr.footballupdate.favorite.Favorite
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.view.FavoriteView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*

class FavoritePresenter(
    private val view: FavoriteView,
    private val context: Context?
) {

    fun getFavorite(isLastMatch: Boolean) {
        val favorites: MutableList<Favorite> = mutableListOf()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date()).toDate()

        context?.database?.use {

            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())

            if (favorite.isNotEmpty()) {
                for (i in favorite.indices) {
                    if (!favorite[i].date.isNullOrEmpty()) {
                        val matchDate = favorite[i].date?.toDate()
                        if (isLastMatch) {
                            if (matchDate?.before(currentDate)!!) {
                                favorites.add(favorite[i])
                            }
                        } else {
                            if (matchDate?.after(currentDate)!! || matchDate == currentDate) {
                                favorites.add(favorite[i])
                            }
                        }
                    }
                }
                if (favorites.isNotEmpty()) {
                    view.showFavorite(favorites)
                } else {
                    view.onFailed()
                }
            } else {
                view.onFailed()
            }
        }
    }
}