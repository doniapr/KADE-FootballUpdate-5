package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

class TeamResponse (
    @SerializedName("teams")
    var teams: List<Team>
)