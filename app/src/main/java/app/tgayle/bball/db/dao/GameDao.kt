package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.db.GameWithTeams

@Dao
interface GameDao {
    @Query("SELECT * FROM Game ORDER BY date")
    fun getGames(): LiveData<List<GameWithTeams>>

    @Query("SELECT * FROM Game WHERE id=:id")
    fun getGame(id: Int): LiveData<GameWithTeams>

    @Query("SELECT * FROM Game WHERE homeTeamId=:teamId OR visitorTeamId=:teamId")
    fun getGamesForTeam(teamId: Int): LiveData<List<GameWithTeams>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game)
}
