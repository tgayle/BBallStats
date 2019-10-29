package app.tgayle.bball.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Team(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    @SerializedName("abbreviation")
    var abbreviation: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("conference")
    var conference: String,
    @SerializedName("division")
    var division: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("name")
    var name: String,
    var favorited: Boolean = false
)