package app.tgayle.bball.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Team(
    @SerializedName("id")
    val id: Int,
    @SerializedName("abbreviation")
    val abbreviation: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("conference")
    val conference: String,
    @SerializedName("division")
    val division: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("name")
    val name: String
)