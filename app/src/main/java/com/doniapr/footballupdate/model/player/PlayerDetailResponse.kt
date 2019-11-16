package com.doniapr.footballupdate.model.player

import com.google.gson.annotations.SerializedName

class PlayerDetailResponse(
    @SerializedName("players")
    val player: List<Player>
)