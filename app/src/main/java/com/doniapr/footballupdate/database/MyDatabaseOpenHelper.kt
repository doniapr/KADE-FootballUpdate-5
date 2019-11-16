package com.doniapr.footballupdate.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.doniapr.footballupdate.model.favorite.FavoriteMatch
import com.doniapr.footballupdate.model.favorite.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteMatch.TABLE_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
            FavoriteMatch.EVENT_NAME to TEXT,
            FavoriteMatch.HOME_TEAM_NAME to TEXT,
            FavoriteMatch.AWAY_TEAM_NAME to TEXT,
            FavoriteMatch.HOME_TEAM_SCORE to TEXT,
            FavoriteMatch.AWAY_TEAM_SCORE to TEXT,
            FavoriteMatch.HOME_TEAM_BADGE to TEXT,
            FavoriteMatch.AWAY_TEAM_BADGE to TEXT,
            FavoriteMatch.LEAGUE_NAME to TEXT,
            FavoriteMatch.ROUND to TEXT,
            FavoriteMatch.DATE to TEXT,
            FavoriteMatch.TIME to TEXT
        )

        db?.createTable(
            FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_LEAGUE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)