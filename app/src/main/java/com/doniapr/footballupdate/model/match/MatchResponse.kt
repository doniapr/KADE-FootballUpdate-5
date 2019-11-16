package com.doniapr.footballupdate.model.match

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    val matches: List<Match>
)