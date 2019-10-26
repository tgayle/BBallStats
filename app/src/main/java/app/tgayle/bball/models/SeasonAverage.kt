package app.tgayle.bball.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SeasonAverage(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("games_played")
    var gamesPlayed: Int,
    @SerializedName("player_id")
    var playerId: Int,
    @SerializedName("season")
    var season: Int,
    @SerializedName("min")
    var minutesPlayed: String,
    @SerializedName("fgm")
    var fieldGoalsMade: Double,
    @SerializedName("fga")
    var fieldGoalsAttempted: Double,
    @SerializedName("fg3m")
    var threePointFGsMade: Double,
    @SerializedName("fg3a")
    var threePointFGsAttempted: Double,
    @SerializedName("ftm")
    var freeThrowsMade: Double,
    @SerializedName("fta")
    var freeThrowsAttempted: Double,
    @SerializedName("oreb")
    var offensiveRebounds: Double,
    @SerializedName("dreb")
    var defensiveRebounds: Double,
    @SerializedName("reb")
    var rebounds: Double,
    @SerializedName("ast")
    var assists: Double,
    @SerializedName("stl")
    var steals: Double,
    @SerializedName("blk")
    var blocks: Double,
    @SerializedName("turnover")
    var turnover: Double,
    @SerializedName("pf")
    var personalFouls: Double,
    @SerializedName("pts")
    var points: Double,
    @SerializedName("fg_pct")
    var fieldGoalPercent: Double,
    @SerializedName("fg3_pct")
    var threePointFGPercent: Double,
    @SerializedName("ft_pct")
    var freeThrowPercent: Double
)