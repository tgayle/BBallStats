package app.tgayle.bball

fun getTeamLogo(teamAbbv: String): Int = when (teamAbbv) {
    "ATL" -> R.drawable.hawks_logo
    "BOS" -> R.drawable.celtics_logo
    "BKN" -> R.drawable.nets_logo
    "CHA" -> R.drawable.hornets_logo
    "CHI" -> R.drawable.bulls_logo
    "CLE" -> R.drawable.cavaliers_logo
    "DAL" -> R.drawable.mavericks_logo
    "DEN" -> R.drawable.nuggets_logo
    "DET" -> R.drawable.pistons_logo
    "GSW" -> R.drawable.warriors_logo
    "HOU" -> R.drawable.rockets_logo
    "IND" -> R.drawable.pacers_logo
    "LAC" -> R.drawable.clippers_logo
    "LAL" -> R.drawable.lakers_logo
    "MEM" -> R.drawable.grizzlies_logo
    "MIA" -> R.drawable.heat_logo
    "MIL" -> R.drawable.bucks_logo
    "MIN" -> R.drawable.timberwolves_logo
    "NOP" -> R.drawable.pelicans_logo
    "NYK" -> R.drawable.knicks_logo
    "OKC" -> R.drawable.thunder_logo
    "ORL" -> R.drawable.magic_logo
    "PHI" -> R.drawable.seventysixers_logo
    "PHX" -> R.drawable.suns_logo
    "POR" -> R.drawable.blazers_logo
    "SAC" -> R.drawable.kings_logo
    "SAS" -> R.drawable.spurs_logo
    "TOR" -> R.drawable.raptors_logo
    "UTA" -> R.drawable.jazz_logo
    "WAS" -> R.drawable.wizards_logo
    else -> R.drawable.help
}