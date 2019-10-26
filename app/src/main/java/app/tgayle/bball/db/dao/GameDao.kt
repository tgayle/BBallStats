package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import app.tgayle.bball.models.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM Game ORDER BY date")
    fun getGames(): LiveData<Game>

    @Query("SELECT * FROM Game WHERE id=:id")
    fun getGame(id: Int): LiveData<Game>

    @Query("SELECT * FROM Game WHERE homeTeamId=:teamId OR visitorTeamId=:teamId")
    fun getGamesForTeam(teamId: Int): LiveData<List<Game>>
}
