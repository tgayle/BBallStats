package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import app.tgayle.bball.models.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM Player")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM Player WHERE teamId=:teamId")
    fun getTeamPlayers(teamId: Int): LiveData<List<Player>>
}
