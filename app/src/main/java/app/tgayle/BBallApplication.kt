package app.tgayle

import android.app.Application
import android.content.Context
import androidx.room.Room
import app.tgayle.bball.db.BasketballDatabase
import app.tgayle.bball.network.BasketballService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BBallApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        prepareDatabase(applicationContext)
    }

    companion object {
        val basketballService = Retrofit.Builder()
            .baseUrl("https://www.balldontlie.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BasketballService::class.java)

        lateinit var database: BasketballDatabase
        fun prepareDatabase(context: Context) {
            database =
                Room.databaseBuilder(context, BasketballDatabase::class.java, "basketball").build()
        }
    }
}