package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

data class LeagueDetail(
    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strBadge")
    var leagueBadge: String? = null,

    @SerializedName("strCountry")
    var leagueCountry: String? = null,

    @SerializedName("strWebsite")
    var leagueWebsite: String? = null,

    @SerializedName("strFacebook")
    var leagueFacebook: String? = null,

    @SerializedName("strTwitter")
    var leagueTwitter: String? = null,

    @SerializedName("strYoutube")
    var leagueYoutube: String? = null

)