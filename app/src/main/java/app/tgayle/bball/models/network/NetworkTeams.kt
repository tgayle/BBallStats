package app.tgayle.bball.models.network

import app.tgayle.bball.models.Team
import com.google.gson.annotations.SerializedName

data class NetworkTeams(
    @SerializedName("data")
    val teams: List<Team>,
    @SerializedName("meta")
    val meta: Meta
)