package app.tgayle.bball.models


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameStats(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("ast")
    var assists: Int,
    @SerializedName("blk")
    var blocks: Int,
    @SerializedName("dreb")
    var defensiveRebounds: Int,
    @SerializedName("fg3_pct")
    var threePointFGPercent: Double,
    @SerializedName("fg3a")
    var threePointFGsAttempted: Int,
    @SerializedName("fg3m")
    var threePointFGsMade: Int,
    @SerializedName("fg_pct")
    var fieldGoalPercent: Double,
    @SerializedName("fga")
    var fieldGoalsAttempted: Int,
    @SerializedName("fgm")
    var fieldGoalsMade: Int,
    @SerializedName("ft_pct")
    var freeThrowPercent: Double,
    @SerializedName("fta")
    var freeThrowsAttempted: Int,
    @SerializedName("ftm")
    var freeThrowsMade: Int,

    @Ignore
    @SerializedName("game")
    var game: Game?,
    @SerializedName("min")
    var min: String,
    @SerializedName("oreb")
    var offensiveRebounds: Int,
    @SerializedName("pf")
    var personalFouls: Int,

    @Ignore
    @SerializedName("player")
    var player: Player?,
    @SerializedName("pts")
    var points: Int,
    @SerializedName("reb")
    var rebounds: Int,
    @SerializedName("stl")
    var steals: Int,

    @Ignore
    @SerializedName("team")
    var team: Team?,
    @SerializedName("turnover")
    var turnover: Int,

    var teamId: Int = team?.id ?: 0,
    var gameId: Int = game?.id ?: 0,
    var playerId: Int = player?.id ?: 0
) {
    constructor(
        id: Int,
        assists: Int,
        blocks: Int,
        defensiveRebounds: Int,
        threePointFGPercent: Double,
        threePointFGsAttempted: Int,
        threePointFGsMade: Int,
        fieldGoalPercent: Double,
        fieldGoalsAttempted: Int,
        fieldGoalsMade: Int,
        freeThrowPercent: Double,
        freeThrowsAttempted: Int,
        freeThrowsMade: Int,
        min: String,
        offensiveRebounds: Int,
        personalFouls: Int,
        points: Int,
        rebounds: Int,
        steals: Int,
        turnover: Int,
        teamId: Int,
        gameId: Int,
        playerId: Int
    ) : this(
        id, assists, blocks, defensiveRebounds, threePointFGPercent, threePointFGsAttempted,
        threePointFGsMade, fieldGoalPercent, fieldGoalsAttempted, fieldGoalsMade, freeThrowPercent,
        freeThrowsAttempted, freeThrowsMade, null, min, offensiveRebounds, personalFouls, null,
        points, rebounds, steals, null, turnover, teamId, gameId, playerId
    )
}