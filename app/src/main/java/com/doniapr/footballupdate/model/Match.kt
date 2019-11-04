package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("idEvent")
    var eventId: Int? = 0,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("idHomeTeam")
    var idHomeTeam: Int? = null,

    @SerializedName("idAwayTeam")
    var idAwayTeam: Int? = null,

    @SerializedName("intHomeScore")
    var homeScore: Int? = null,

    @SerializedName("intAwayScore")
    var awayScore: Int? = null,

    @SerializedName("intRound")
    var round: Int? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strTime")
    var time: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strThumb")
    var matchBanner: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalDetail: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCard: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCard: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGK: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDF: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMF: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeCF: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubtitute: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCard: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCard: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalDetail: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGK: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDF: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMF: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayCF: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubtitute: String? = null,

    @SerializedName("intHomeShots")
    var homeShot: String? = null,

    @SerializedName("intAwayShots")
    var awayShot: String? = null

)