package com.doniapr.footballupdate.model.league

import com.google.gson.annotations.SerializedName

data class LeagueDetailResponse(
    @SerializedName("leagues")
    val league: List<LeagueDetail>
)