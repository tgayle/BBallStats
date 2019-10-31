package app.tgayle.bball

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, _, _ ->
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_activity, menu)

        val favoriteTeamsItem = menu?.findItem(R.id.chooseTeams)

        favoriteTeamsItem?.let {
            it.isVisible =
                findNavController(R.id.nav_host_fragment).currentDestination?.id != R.id.onboardingFragment
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.chooseTeams -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.onboardingFragment)
                true
            }
            else -> false
        }
    }

}
