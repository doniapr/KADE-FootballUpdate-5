package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    val matchs: List<Match>
)