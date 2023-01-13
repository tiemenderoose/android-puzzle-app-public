package com.example.puzzleapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabasePuzzle::class], version = 4, exportSchema = false)
abstract class PuzzleDatabase : RoomDatabase() {

    abstract val puzzleDatabaseDao: PuzzleDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PuzzleDatabase? = null
        fun getInstance(context: Context): PuzzleDatabase {
            var instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PuzzleDatabase::class.java,
                        "puzzle_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance!!
            }
        }
    }
}
