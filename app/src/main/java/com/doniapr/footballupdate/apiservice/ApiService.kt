package com.doniapr.footballupdate.apiservice

import com.doniapr.footballupdate.BuildConfig
import com.doniapr.footballupdate.model.league.LeagueDetailResponse
import com.doniapr.footballupdate.model.match.MatchResponse
import com.doniapr.footballupdate.model.player.PlayerDetailResponse
import com.doniapr.footballupdate.model.player.PlayerResponse
import com.doniapr.footballupdate.model.search.SearchResponse
import com.doniapr.footballupdate.model.standing.StandingsResponse
import com.doniapr.footballupdate.model.team.TeamLastMatchResponse
import com.doniapr.footballupdate.model.team.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // League
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupleague.php")
    suspend fun getDetailLeague(@Query("id") id: String?): Response<LeagueDetailResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php")
    suspend fun getLastMatch(@Query("id") id: String?): Response<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php")
    suspend fun getNextMatch(@Query("id") id: String?): Response<MatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_teams.php")
    suspend fun getTeam(@Query("id") id: String, @Query("s") s: String): Response<TeamResponse>

    // Search
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/searchevents.php")
    suspend fun searchMatch(@Query("e") e: String?): Response<SearchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/searchteams.php")
    suspend fun searchTeam(@Query("t") t: String?): Response<TeamResponse>

    // Match
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php")
    suspend fun getMatchDetail(@Query("id") id: String): Response<MatchResponse>

    // Team
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php")
    suspend fun getTeamInfo(@Query("id") id: String): Response<TeamResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventslast.php")
    suspend fun getTeamLastMatch(@Query("id") id: String): Response<TeamLastMatchResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnext.php")
    suspend fun getTeamNextMatch(@Query("id") id: String): Response<MatchResponse>

    // Standing
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookuptable.php")
    suspend fun getStanding(@Query("l") l: String, @Query("s") s: String): Response<StandingsResponse>

    // Player
    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_players.php")
    suspend fun getTeamPlayer(@Query("id") id: String): Response<PlayerResponse>

    @GET("api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupplayer.php")
    suspend fun getPlayerDetail(@Query("id") id: Int): Response<PlayerDetailResponse>


}