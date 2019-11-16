package com.doniapr.footballupdate.model.team

import com.doniapr.footballupdate.model.match.Match
import com.google.gson.annotations.SerializedName

data class TeamLastMatchResponse(
    @SerializedName("results")
    val matches: List<Match>
)