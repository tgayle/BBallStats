package app.tgayle.bball.models.network

import app.tgayle.bball.models.GameStats
import com.google.gson.annotations.SerializedName

data class NetworkStats(
    @SerializedName("data")
    val stats: List<GameStats>,
    @SerializedName("meta")
    val meta: Meta
)