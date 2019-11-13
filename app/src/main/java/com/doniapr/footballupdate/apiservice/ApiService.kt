package com.doniapr.footballupdate.apiservice

import com.doniapr.footballupdate.BuildConfig
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupleague.php")
    suspend fun getDetailLeague(@Query("id") id: String?): Response<LeagueDetailResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php")
    suspend fun getLastMatch(@Query("id") id: String?): Response<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php")
    suspend fun getNextMatch(@Query("id") id: String?): Response<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/searchevents.php")
    suspend fun searchMatch(@Query("e") e: String?): Response<SearchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php")
    suspend fun getMatchDetail(@Query("id") id: String): Response<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php")
    suspend fun getTeamInfo(@Query("id") id: String): Response<TeamResponse>
}