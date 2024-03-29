package com.mourad.rockpaperscissors.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mourad.rockpaperscissors.R
import com.mourad.rockpaperscissors.database.GameRepository
import com.mourad.rockpaperscissors.model.Game
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository
    private var games = arrayListOf<Game>()
    private var gameAdapter = GameAdapter(games)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.activity_history_title)

        rvGames.adapter = gameAdapter
        rvGames.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGames.addItemDecoration(DividerItemDecoration(this@HistoryActivity, DividerItemDecoration.VERTICAL))
        getGamesFromDatabase()

    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val gamesList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames().sortedByDescending { it.date }
            }
            this@HistoryActivity.games.clear()
            this@HistoryActivity.games.addAll(gamesList)
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGamesHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDatabase()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_delete_history -> {
                deleteGamesHistory()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
