package app.tgayle.bball.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import app.tgayle.bball.models.Team
import app.tgayle.bball.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class WelcomeViewModel : BaseViewModel() {
    val loading = MutableLiveData(false)
    val message = MutableLiveData<String>()
    val teams = database.teams().getTeams()
    val continueAllowed = Transformations.map(teams) {
        it.any { team -> team.favorited }
    }

    fun loadTeams() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teams = service.getTeams(0)
                database.teams().insert(teams.teams)
                message.postValue(null)
            } catch (err: IOException) {
                message.postValue("No internet connection available!")
            }

            loading.postValue(false)
        }
    }

    fun toggleFavorite(team: Team) {
        GlobalScope.launch(Dispatchers.IO) {
            database.teams().update(team.copy(favorited = !team.favorited))
        }
    }
}
