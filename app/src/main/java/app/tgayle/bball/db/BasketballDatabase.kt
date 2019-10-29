package app.tgayle.bball.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.tgayle.bball.db.dao.*
import app.tgayle.bball.models.Game
import app.tgayle.bball.models.GameStats
import app.tgayle.bball.models.Player
import app.tgayle.bball.models.Team
import app.tgayle.bball.models.db.PlayerTeamJoin
import app.tgayle.bball.models.db.TeamGameJoin

@Database(
    entities = [
        Game::class, GameStats::class, Player::class,
        Team::class, TeamGameJoin::class, PlayerTeamJoin::class
    ],
    version = 1
)
abstract class BasketballDatabase : RoomDatabase() {
    abstract fun games(): GameDao
    abstract fun stats(): StatDao
    abstract fun players(): PlayerDao
    abstract fun teams(): TeamDao
    abstract fun teamGameJoin(): TeamGameJoinDao
    abstract fun playerTeamJoin(): PlayerTeamJoinDao
}