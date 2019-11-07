package com.doniapr.footballupdate.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.doniapr.footballupdate.favorite.Favorite
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1){

    companion object{
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabaseOpenHelper{
            if (instance == null){
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.EVENT_NAME to TEXT,
            Favorite.HOME_TEAM_NAME to TEXT,
            Favorite.AWAY_TEAM_NAME to TEXT,
            Favorite.HOME_TEAM_SCORE to TEXT,
            Favorite.AWAY_TEAM_SCORE to TEXT,
            Favorite.HOME_TEAM_BADGE to TEXT,
            Favorite.AWAY_TEAM_BADGE to TEXT,
            Favorite.LEAGUE_NAME to TEXT,
            Favorite.ROUND to TEXT,
            Favorite.DATE to TEXT,
            Favorite.TIME to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?. dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)