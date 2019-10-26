package app.tgayle.bball.models.network

import app.tgayle.bball.models.Player
import com.google.gson.annotations.SerializedName

data class NetworkPlayer(
    @SerializedName("data")
    val players: List<Player>,
    @SerializedName("meta")
    val meta: Meta
)