package app.tgayle.bball.ui.onboarding

import androidx.lifecycle.Transformations
import app.tgayle.bball.models.Team
import app.tgayle.bball.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WelcomeViewModel : BaseViewModel() {
    val teams = database.teams().getTeams()
    val continueAllowed = Transformations.map(teams) {
        it.any { team -> team.favorited }
    }

    fun toggleFavorite(team: Team) {
        GlobalScope.launch(Dispatchers.IO) {
            database.teams().update(team.copy(favorited = !team.favorited))
        }
    }
}
