package app.tgayle.bball.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Player(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("position")
    var position: String,

    @Ignore
    @SerializedName("team")
    var team: Team?,

    @SerializedName("height_feet")
    var heightFeet: Int?,
    @SerializedName("height_inches")
    var heightInches: Int?,
    @SerializedName("weight_pounds")
    var weightPounds: Int?,

    var teamId: Int = team?.id ?: 0
) {
    constructor(
        id: Int,
        firstName: String,
        lastName: String,
        position: String,
        heightFeet: Int?,
        heightInches: Int?,
        weightPounds: Int?,
        teamId: Int
    ) : this(
        id,
        firstName,
        lastName,
        position,
        null,
        heightFeet,
        heightInches,
        weightPounds,
        teamId
    )
}