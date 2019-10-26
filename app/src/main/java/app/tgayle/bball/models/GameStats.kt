package app.tgayle.bball.models


import com.google.gson.annotations.SerializedName

data class GameStats(
    @SerializedName("id")
    val id: Int,
    @SerializedName("ast")
    val assists: Int,
    @SerializedName("blk")
    val blocks: Int,
    @SerializedName("dreb")
    val defensiveRebounds: Int,
    @SerializedName("fg3_pct")
    val threePointFGPercent: Double,
    @SerializedName("fg3a")
    val threePointFGsAttempted: Int,
    @SerializedName("fg3m")
    val threePointFGsMade: Int,
    @SerializedName("fg_pct")
    val fieldGoalPercent: Double,
    @SerializedName("fga")
    val fieldGoalsAttempted: Int,
    @SerializedName("fgm")
    val fieldGoalsMade: Int,
    @SerializedName("ft_pct")
    val freeThrowPercent: Double,
    @SerializedName("fta")
    val freeThrowsAttempted: Int,
    @SerializedName("ftm")
    val freeThrowsMade: Int,
    @SerializedName("game")
    val game: Game,
    @SerializedName("min")
    val min: String,
    @SerializedName("oreb")
    val offensiveRebounds: Int,
    @SerializedName("pf")
    val personalFouls: Int,
    @SerializedName("player")
    val player: Player,
    @SerializedName("pts")
    val points: Int,
    @SerializedName("reb")
    val rebounds: Int,
    @SerializedName("stl")
    val steals: Int,
    @SerializedName("team")
    val team: Team,
    @SerializedName("turnover")
    val turnover: Int
)