package app.tgayle.bball.ui

import androidx.lifecycle.ViewModel
import app.tgayle.BBallApplication

open class BaseViewModel : ViewModel() {
    val service = BBallApplication.basketballService
    val database = BBallApplication.database
}