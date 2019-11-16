package com.doniapr.footballupdate.presenter

import android.content.Context
import com.doniapr.footballupdate.database.database
import com.doniapr.footballupdate.model.favorite.FavoriteMatch
import com.doniapr.footballupdate.model.favorite.FavoriteTeam
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.view.viewinterface.FavoriteView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*

class FavoritePresenter(
    private val view: FavoriteView,
    private val context: Context?
) {

    fun getFavorite(isLastMatch: Boolean) {
        val favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(Date()).toDate()

        context?.database?.use {

            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            if (favorite.isNotEmpty()) {
                for (i in favorite.indices) {
                    if (!favorite[i].date.isNullOrEmpty()) {
                        val matchDate = favorite[i].date?.toDate()
                        if (isLastMatch) {
                            if (matchDate?.before(currentDate)!!) {
                                favoriteMatches.add(favorite[i])
                            }
                        } else {
                            if (matchDate?.after(currentDate)!! || matchDate == currentDate) {
                                favoriteMatches.add(favorite[i])
                            }
                        }
                    }
                }
                if (favoriteMatches.isNotEmpty()) {
                    view.showFavorite(favoriteMatches)
                } else {
                    view.onFailed()
                }
            } else {
                view.onFailed()
            }
        }
    }

    fun getFavoriteTeam() {

        context?.database?.use {

            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())

            if (favorite.isNotEmpty()) {
                favorite.let {
                    view.showFavoriteTeam(it)
                }
            } else {
                view.onFailed()
            }
        }
    }
}