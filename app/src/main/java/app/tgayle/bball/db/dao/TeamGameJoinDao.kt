package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.db.TeamGameJoin

@Dao
abstract class TeamGameJoinDao {

    @Query(
        """SELECT * FROM Game
        JOIN team_game_join   ON game.id=team_game_join.gameId
        JOIN Team homeTeam    ON homeTeam.id=team_game_join.homeTeamId
        JOIN Team visitorTeam ON visitorTeam.id=team_game_join.visitorTeamId
        WHERE Game.id = :gameId
        """
    )
    abstract fun getGame(gameId: Int): LiveData<Game>

    @Query(
        """SELECT * FROM Game
        JOIN team_game_join   ON game.id=team_game_join.gameId
        JOIN Team homeTeam    ON homeTeam.id=team_game_join.homeTeamId
        JOIN Team visitorTeam ON visitorTeam.id=team_game_join.visitorTeamId
        """
    )
    abstract fun getGames(): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(join: TeamGameJoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(joins: List<TeamGameJoin>)


}
