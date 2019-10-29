package app.tgayle.bball.ui.recentgames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.Team
import app.tgayle.bball.models.db.TeamGameJoin
import app.tgayle.bball.models.network.Meta
import app.tgayle.bball.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecentGamesViewModel : BaseViewModel() {

    val refreshing = MutableLiveData(false)
    val recentGames = Transformations.map(database.games().getGames()) {
        it.map { game -> game.simple() }
    }
    val selectedTeam = MutableLiveData<Team>()
    val currentSeason = MutableLiveData(2019)
    val currentSeasonRange = Transformations.map(currentSeason) { year -> "$year-${year + 1}" }
    val favoritedTeams = database.teams().getFavoritedTeams()

    var lastLoadMeta: Meta? = null

    fun refresh() {
        refreshing.value = true

        GlobalScope.launch(Dispatchers.IO) {
            val games = service.getGames(
                lastLoadMeta?.nextPage ?: 0,
                teamIds = listOf(selectedTeam.value?.id),
                seasons = listOf(currentSeason.value)
            )

            lastLoadMeta = games.meta

            val joins = mutableListOf<TeamGameJoin>()
            val teams = mutableListOf<Team?>()

            val fixedIds = mutableListOf<Game>()


            for (game in games.games) {
                println(game.homeTeam?.id ?: "empty id for ${game.id}")
                teams += listOf(game.visitorTeam, game.homeTeam)
                joins += listOf(
                    TeamGameJoin(
                        game.visitorTeam!!.id,
                        game.homeTeam!!.id,
                        game.id
                    )
                )
                fixedIds += game.copy(
                    homeTeamId = game.homeTeam!!.id,
                    visitorTeamId = game.visitorTeam!!.id
                )
            }

            database.teams().insert(teams)
            database.games().insert(fixedIds)
            database.teamGameJoin().insert(joins)
            refreshing.postValue(false)
        }
    }

    fun selectNewTeam(newTeam: Team) {
        selectedTeam.value = newTeam
        refresh()
    }

    fun selectNewSeason(year: Int) {
        currentSeason.value = year
        refresh()
    }
}
