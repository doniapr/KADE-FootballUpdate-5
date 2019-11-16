package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer")
    var idPlayer: String?,

    @SerializedName("strNationality")
    var nationality: String?,

    @SerializedName("strPlayer")
    var namePlayer: String?,

    @SerializedName("strTeam")
    var team: String?,

    @SerializedName("dateBorn")
    var dateBorn: String?,

    @SerializedName("strNumber")
    var number: String?,

    @SerializedName("dateSigned")
    var dateSigned: String?,

    @SerializedName("strSigning")
    var strSigning: String?,

    @SerializedName("strBirthLocation")
    var birthLocation: String?,

    @SerializedName("strDescriptionEN")
    var desc: String?,

    @SerializedName("strPosition")
    var position: String?,

    @SerializedName("strCutout")
    var image: String?,

    @SerializedName("strThumb")
    var imageThumb: String?,

    @SerializedName("strHeight")
    var height: String?,

    @SerializedName("strWeight")
    var weight: String?
)