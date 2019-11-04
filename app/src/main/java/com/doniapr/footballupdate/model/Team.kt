package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

class Team(
    @SerializedName("idTeam")
    var teamId: Int? = 0,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strLeague")
    var teamLeague: String? = null,

    @SerializedName("strCountry")
    var teamCountry: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)