package com.doniapr.footballupdate.model.standing

import com.google.gson.annotations.SerializedName

data class Standings(
    @SerializedName("name")
    var teamName: String? = "",

    @SerializedName("teamid")
    var teamId: String? = "",

    @SerializedName("played")
    var played: String? = "",

    @SerializedName("goalsfor")
    var goalFor: String? = "",

    @SerializedName("goalsagainst")
    var goalAgainst: String? = "",

    @SerializedName("goalsdifference")
    var goalDifference: String? = "",

    @SerializedName("win")
    var win: String? = "",

    @SerializedName("draw")
    var draw: String? = "",

    @SerializedName("loss")
    var loss: String? = "",

    @SerializedName("total")
    var total: String? = ""
)