package app.tgayle.bball.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.tgayle.bball.models.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team WHERE favorited = 1")
    fun getFavoritedTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM Team")
    fun getTeams(): LiveData<List<Team>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(teams: List<Team?>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(team: Team)

    @Query("SELECT * FROM Team WHERE abbreviation=:abbv")
    suspend fun getByAbbreviation(abbv: String): Team?
}
