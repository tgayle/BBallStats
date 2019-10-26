package app.tgayle.bball.ui.gamedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import app.tgayle.bball.ui.BaseViewModel

class GameDetailsViewModel : BaseViewModel() {
    private val currentGameId = MutableLiveData(0)

    val currentGame = Transformations.switchMap(currentGameId) {
        database.teamGameJoin().getGame(it)
    }

    val loading = Transformations.map(currentGame) { it == null }

    fun setGame(gameId: Int) {
        currentGameId.value = gameId
    }
}
