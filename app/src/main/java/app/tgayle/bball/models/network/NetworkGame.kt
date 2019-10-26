package app.tgayle.bball.models.network

import app.tgayle.bball.models.Game
import com.google.gson.annotations.SerializedName

data class NetworkGame(
    @SerializedName("data")
    val games: List<Game>,
    @SerializedName("meta")
    val meta: Meta
)