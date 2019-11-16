package com.doniapr.footballupdate.model.team

import com.google.gson.annotations.SerializedName

class Team(
    @SerializedName("idTeam")
    var teamId: Int? = 0,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strLeague")
    var teamLeague: String? = null,

    @SerializedName("strSport")
    var teamSport: String? = null,

    @SerializedName("strCountry")
    var teamCountry: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null,

    @SerializedName("strStadium")
    var teamStadium: String? = null,

    @SerializedName("strStadiumThumb")
    var teamStadiumThumb: String? = null,

    @SerializedName("strStadiumDescription")
    var teamStadiumDesc: String? = null,

    @SerializedName("strDescriptionEN")
    var teamDescription: String? = null
)