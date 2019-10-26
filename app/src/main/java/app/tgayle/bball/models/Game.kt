package app.tgayle.bball.models

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("home_team")
    val homeTeam: Team,
    @SerializedName("home_team_score")
    val homeTeamScore: Int,
    @SerializedName("period")
    val period: Int,
    @SerializedName("postseason")
    val postseason: Boolean,
    @SerializedName("season")
    val season: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("visitor_team")
    val visitorTeam: Team,
    @SerializedName("visitor_team_score")
    val visitorTeamScore: Int
)