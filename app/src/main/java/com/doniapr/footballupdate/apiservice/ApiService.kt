package com.doniapr.footballupdate.apiservice

import com.doniapr.footballupdate.BuildConfig
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupleague.php")
    fun getDetailLeague(@Query("id") id: String?): Call<LeagueDetailResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php")
    fun getLastMatch(@Query("id") id: String?): Call<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String?): Call<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/searchevents.php")
    fun searchMatch(@Query("e") e: String?): Call<SearchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php")
    fun getMatchDetail(@Query("id") id: String): Call<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php")
    fun getTeamInfo(@Query("id") id: String): Call<TeamResponse>
}