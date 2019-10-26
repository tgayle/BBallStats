package app.tgayle.bball.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.tgayle.bball.models.*

@Database(
    entities = [
        Game::class, GameStats::class, Player::class,
        Team::class, TeamGameJoin::class, PlayerTeamJoin::class
    ],
    version = 1
)
abstract class BasketballDatabase : RoomDatabase() {
    
}