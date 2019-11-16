package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

data class TeamLastMatchResponse(
    @SerializedName("results")
    val matches: List<Match>
)