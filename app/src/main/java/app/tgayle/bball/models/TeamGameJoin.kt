package app.tgayle.bball.models

import androidx.room.Entity
import androidx.room.ForeignKey

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