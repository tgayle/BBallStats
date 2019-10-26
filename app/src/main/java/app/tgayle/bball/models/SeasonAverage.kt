package app.tgayle.bball.models

import com.google.gson.annotations.SerializedName

data class SeasonAverage(
    @SerializedName("games_played")
    val gamesPlayed: Int,
    @SerializedName("player_id")
    val playerId: Int,
    @SerializedName("season")
    val season: Int,
    @SerializedName("min")
    val minutesPlayed: String,
    @SerializedName("fgm")
    val fieldGoalsMade: Double,
    @SerializedName("fga")
    val fieldGoalsAttempted: Double,
    @SerializedName("fg3m")
    val threePointFGsMade: Double,
    @SerializedName("fg3a")
    val threePointFGsAttempted: Double,
    @SerializedName("ftm")
    val freeThrowsMade: Double,
    @SerializedName("fta")
    val freeThrowsAttempted: Double,
    @SerializedName("oreb")
    val offensiveRebounds: Double,
    @SerializedName("dreb")
    val defensiveRebounds: Double,
    @SerializedName("reb")
    val rebounds: Double,
    @SerializedName("ast")
    val assists: Double,
    @SerializedName("stl")
    val steals: Double,
    @SerializedName("blk")
    val blocks: Double,
    @SerializedName("turnover")
    val turnover: Double,
    @SerializedName("pf")
    val personalFouls: Double,
    @SerializedName("pts")
    val points: Double,
    @SerializedName("fg_pct")
    val fieldGoalPercent: Double,
    @SerializedName("fg3_pct")
    val threePointFGPercent: Double,
    @SerializedName("ft_pct")
    val freeThrowPercent: Double
)