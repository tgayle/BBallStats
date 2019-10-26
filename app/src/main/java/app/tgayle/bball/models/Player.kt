package app.tgayle.bball.models

import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

@Entity
data class Player(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("position")
    val position: String,

    @Ignore
    @SerializedName("team")
    val team: Team,

    @SerializedName("height_feet")
    val heightFeet: Int?,
    @SerializedName("height_inches")
    val heightInches: Int?,
    @SerializedName("weight_pounds")
    val weightPounds: Int?,

    val teamId: Int = team.id
)