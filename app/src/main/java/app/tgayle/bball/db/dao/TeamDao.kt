package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.tgayle.bball.models.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team WHERE favorited = 1")
    fun getFavoritedTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM Team")
    fun getTeams(): LiveData<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(teams: List<Team?>)

}
