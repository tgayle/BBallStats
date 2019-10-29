package app.tgayle.bball.models.db

import androidx.room.Entity
import androidx.room.ForeignKey
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.Team

@Entity(
    tableName = "team_game_join",
    primaryKeys = ["visitorTeamId", "homeTeamId", "gameId"],
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("visitorTeamId")
        ),
        ForeignKey(
            entity = Team::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("homeTeamId")
        ),
        ForeignKey(
            entity = Game::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gameId")
        )
    ]
)
data class TeamGameJoin(
    val visitorTeamId: Int,
    val homeTeamId: Int,
    val gameId: Int
)