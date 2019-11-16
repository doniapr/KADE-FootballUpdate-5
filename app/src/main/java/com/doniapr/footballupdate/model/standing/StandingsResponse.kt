package com.doniapr.footballupdate.model.standing

import com.google.gson.annotations.SerializedName

data class StandingsResponse(
    @SerializedName("table")
    val standing: List<Standings>
)