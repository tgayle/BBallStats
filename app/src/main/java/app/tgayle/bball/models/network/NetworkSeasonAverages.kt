package app.tgayle.bball.models.network

import app.tgayle.bball.models.SeasonAverage
import com.google.gson.annotations.SerializedName

data class NetworkSeasonAverages(
    @SerializedName("data")
    val averages: List<SeasonAverage>
)