package com.doniapr.footballupdate.favorite

data class Favorite(
    val id: Long?,
    val eventId: String?,
    val eventName: String?,
    val homeTeamName: String?,
    val awayTeamName: String?,
    val homeTeamScore: String?,
    val awayTeamScore: String?,
    val homeTeamBadge: String?,
    val awayTeamBadge: String?,
    val leagueName: String?,
    val round: String?,
    val date: String?,
    val time: String?
) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_NAME: String = "EVENT_NAME"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val HOME_TEAM_BADGE: String = "HOME_TEAM_BADGE"
        const val AWAY_TEAM_BADGE: String = "AWAY_TEAM_BADGE"
        const val LEAGUE_NAME: String = "LEAGUE_NAME"
        const val ROUND: String = "ROUND"
        const val DATE: String = "DATE"
        const val TIME: String = "TIME"
    }
}

