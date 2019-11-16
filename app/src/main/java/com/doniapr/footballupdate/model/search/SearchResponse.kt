package com.doniapr.footballupdate.model.search

import com.doniapr.footballupdate.model.match.Match
import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("event")
    val matches: List<Match>
)