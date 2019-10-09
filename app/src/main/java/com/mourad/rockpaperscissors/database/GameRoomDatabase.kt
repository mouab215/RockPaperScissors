package com.mourad.rockpaperscissors.database

import android.content.Context
import androidx.room.*
import com.mourad.rockpaperscissors.model.Game
import com.mourad.rockpaperscissors.util.Converters

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameRoomDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var GameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (GameRoomDatabaseInstance == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (GameRoomDatabaseInstance == null) {
                        GameRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                GameRoomDatabase::class.java,
                                DATABASE_NAME
                            )
                                .build()
                    }
                }
            }
            return GameRoomDatabaseInstance
        }
    }

}