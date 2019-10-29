package app.tgayle.bball.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Game(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @SerializedName("date")
    var date: String,

    @Ignore
    @SerializedName("home_team")
    var homeTeam: Team?,

    @SerializedName("home_team_score")
    var homeTeamScore: Int,

    @SerializedName("period")
    var period: Int,

    @SerializedName("postseason")
    var postseason: Boolean,

    @SerializedName("season")
    var season: Int,

    @SerializedName("status")
    var status: String,

    @SerializedName("time")
    var time: String,

    @Ignore
    @SerializedName("visitor_team")
    var visitorTeam: Team?,

    @SerializedName("visitor_team_score")
    var visitorTeamScore: Int,
    var homeTeamId: Int = homeTeam?.id ?: 0,
    var visitorTeamId: Int = visitorTeam?.id ?: 0
) {
    @Ignore
    val seasonRange = "$season-${season + 1}"

    constructor(
        id: Int,
        date: String,
        homeTeamScore: Int,
        period: Int,
        postseason: Boolean,
        season: Int,
        status: String,
        time: String,
        visitorTeamScore: Int,
        homeTeamId: Int,
        visitorTeamId: Int
    ) : this(
        id, date, null, homeTeamScore, period, postseason, season, status, time,
        null, visitorTeamScore, homeTeamId, visitorTeamId
    )
}