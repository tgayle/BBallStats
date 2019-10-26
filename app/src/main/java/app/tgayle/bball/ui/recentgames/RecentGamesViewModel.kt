package app.tgayle.bball.ui.recentgames

import androidx.lifecycle.MutableLiveData
import app.tgayle.bball.models.network.Meta
import app.tgayle.bball.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecentGamesViewModel : BaseViewModel() {

    val refreshing = MutableLiveData(false)
    val recentGames = database.games().getGames()

    var lastLoadMeta: Meta? = null

    fun refresh() {
        refreshing.value = true

        GlobalScope.launch(Dispatchers.IO) {
            val games = service.getGames(lastLoadMeta?.nextPage ?: 0)
            lastLoadMeta = games.meta

            database.games().insert(games.games)
            refreshing.postValue(false)
        }
    }
}
