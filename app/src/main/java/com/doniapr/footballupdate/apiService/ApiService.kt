package com.doniapr.footballupdate.apiService

import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.model.SearchResponse
import com.doniapr.footballupdate.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/json/1/lookupleague.php")
    fun getDetailLeague(@Query("id") id:String?): Call<LeagueDetailResponse>

    @GET("api/v1/json/1/eventspastleague.php")
    fun getLastMatch(@Query("id") id: String?): Call<MatchResponse>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String?): Call<MatchResponse>

    @GET("api/v1/json/1/searchevents.php")
    fun searchMatch(@Query("e") e: String?): Call<SearchResponse>

    @GET("api/v1/json/1/lookupevent.php")
    fun getMatchDetail(@Query("id") id: String): Call<MatchResponse>

    @GET("api/v1/json/1/lookupteam.php")
    fun getTeamInfo(@Query("id") id: String): Call<TeamResponse>
}