package app.tgayle.bball.models.db

import androidx.room.Embedded
import androidx.room.Relation
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.Team

class GameWithTeams(
    @Embedded
    val game: Game,

    @Relation(parentColumn = "visitorTeamId", entityColumn = "id", entity = Team::class)
    val visitorTeam: List<Team>,

    @Relation(parentColumn = "homeTeamId", entityColumn = "id", entity = Team::class)
    val homeTeam: List<Team>
)