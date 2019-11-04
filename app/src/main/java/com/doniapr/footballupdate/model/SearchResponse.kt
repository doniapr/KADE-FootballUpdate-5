package com.doniapr.footballupdate.model

import com.google.gson.annotations.SerializedName

class SearchResponse (
    @SerializedName("event")
    val matches: List<Match>
)