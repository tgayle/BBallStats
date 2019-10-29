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

    // Relations will return a list even if there's only one item.
    @Relation(parentColumn = "homeTeamId", entityColumn = "id", entity = Team::class)
    val homeTeam: List<Team>
) {
    fun simple() = SimpleGameWithTeams(game, visitorTeam.first(), homeTeam.first())
}

class SimpleGameWithTeams(val game: Game, val visitorTeam: Team, val homeTeam: Team)