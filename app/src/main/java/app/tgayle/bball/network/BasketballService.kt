package app.tgayle.bball.network

import app.tgayle.bball.models.Game
import app.tgayle.bball.models.Player
import app.tgayle.bball.models.Team
import app.tgayle.bball.models.network.NetworkGames
import app.tgayle.bball.models.network.NetworkPlayers
import app.tgayle.bball.models.network.NetworkStats
import app.tgayle.bball.models.network.NetworkTeams
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BasketballService {

    @GET("players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): NetworkPlayers

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): Player

    @GET("teams")
    suspend fun getTeams(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): NetworkTeams

    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: Int): Team

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100,
        @Query("seasons") seasons: List<String>? = null,
        @Query("team_ids") teamIds: List<String>? = null
    ): NetworkGames

    @GET("games/{id}")
    suspend fun getGame(@Path("id") gameId: Int): Game

    @GET("stats")
    suspend fun getStats(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100,
        @Query("seasons") seasons: List<String> = arrayListOf(),
        @Query("game_ids") gameIds: List<String> = arrayListOf()
    ): NetworkStats

    @GET("season_averages")
    suspend fun getSeasonAverages(
        @Query("season") season: String,
        @Query("player_ids") playerIds: List<String>
    )
}