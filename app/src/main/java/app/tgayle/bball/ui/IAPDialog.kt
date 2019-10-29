package app.tgayle.bball.ui

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun buildIAPDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
        .setTitle("This is a premium feature!")
        .setMessage(
            """Premium subscribers get special features, including:
            |- Favorite more than 4 teams
            |- View stats more than 5 years into the past
            |and more!
        """.trimMargin()
        )
        .setCancelable(false)
        .setPositiveButton("Purchase Premium", { dialog, which -> })
        .setNegativeButton("Nah", { dialog, which -> })
        .create()
}