package app.tgayle.bball.ui.recentgames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.Team
import app.tgayle.bball.models.db.TeamGameJoin
import app.tgayle.bball.models.network.Meta
import app.tgayle.bball.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class RecentGamesViewModel : BaseViewModel() {
    val message = MutableLiveData<String>()
    val refreshing = MutableLiveData(false)
    val selectedTeam = MutableLiveData<Team>()
    val currentSeason = MutableLiveData(2018)

    val recentGames = Transformations.switchMap(selectedTeam) { team ->
        Transformations.switchMap(currentSeason) { season ->
            println("${team.fullName} $season")
            Transformations.map(
                database.games().getGamesForTeamBySeason(
                    team?.id,
                    season
                )
            ) { gamesWithTeams ->
                gamesWithTeams.map { game -> game.simple() }
            }
        }
    }

    val currentSeasonRange = Transformations.map(currentSeason) { year -> "$year-${year + 1}" }
    val favoritedTeams = database.teams().getFavoritedTeams()

    var lastLoadMeta: Meta? = null

    fun refresh() {
        refreshing.value = true

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val games = service.getGames(
                    lastLoadMeta?.nextPage ?: 0,
                    teamIds = selectedTeam.value?.id,
                    seasons = currentSeason.value
                )

                lastLoadMeta = games.meta

                val joins = mutableListOf<TeamGameJoin>()
                val teams = mutableListOf<Team?>()

                val fixedIds = mutableListOf<Game>()


                for (game in games.games) {
//                println(game.homeTeam?.id ?: "empty id for ${game.id}")
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
                message.postValue(null)

            } catch (err: IOException) {
                println("Network error.")
                message.postValue("There was an issue updating the scores!")
                err.printStackTrace()
            }

            refreshing.postValue(false)
        }
    }

    fun selectNewTeam(newTeam: Team) {
        selectedTeam.value = newTeam
        lastLoadMeta = null
        refresh()
    }

    fun selectNewSeason(year: Int) {
        currentSeason.value = year
        lastLoadMeta = null
        refresh()
    }

    fun setCurrentTeamFromAbbreviation(abbv: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val team = database.teams().getByAbbreviation(abbv)
            selectedTeam.postValue(team)
        }
    }
}