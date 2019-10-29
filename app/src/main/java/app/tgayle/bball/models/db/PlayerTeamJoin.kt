package app.tgayle.bball.models.db

import androidx.room.Entity
import androidx.room.ForeignKey
import app.tgayle.bball.models.Player
import app.tgayle.bball.models.Team

@Entity(
    tableName = "player_team_join",
    primaryKeys = arrayOf("playerId", "teamId"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Player::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("playerId")
        ),
        ForeignKey(
            entity = Team::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("teamId")
        )
    )
)
data class PlayerTeamJoin(
    val playerId: Int,
    val teamId: Int
)