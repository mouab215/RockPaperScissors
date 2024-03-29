package com.mourad.rockpaperscissors.database

import android.content.Context
import com.mourad.rockpaperscissors.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames() = gameDao.getAllGames()

    suspend fun deleteAllGames() = gameDao.deleteAllGames()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)
}