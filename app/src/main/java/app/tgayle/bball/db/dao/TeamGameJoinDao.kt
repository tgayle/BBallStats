package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import app.tgayle.bball.models.Game

@Dao
interface TeamGameJoinDao {

    @Query(
        """SELECT * FROM Game
        JOIN team_game_join   ON game.id=team_game_join.gameId
        JOIN Team homeTeam    ON homeTeam.id=team_game_join.homeTeamId
        JOIN Team visitorTeam ON visitorTeam.id=team_game_join.visitorTeamId
        WHERE Game.id = :gameId
        """
    )
    fun getGame(gameId: Int): LiveData<Game>
}
