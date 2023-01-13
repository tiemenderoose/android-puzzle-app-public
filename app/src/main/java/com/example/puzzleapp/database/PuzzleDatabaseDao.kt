package com.example.puzzleapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PuzzleDatabaseDao {
    @Insert
    suspend fun insert(puzzle: DatabasePuzzle)

    @Update
    suspend fun update(puzzle: DatabasePuzzle)

    @Query("SELECT * FROM puzzle_table WHERE puzzle_id = :key")
    suspend fun get(key: Long): DatabasePuzzle?

    @Query("SELECT * FROM puzzle_table ORDER BY completion_date ASC, puzzle_id DESC")
    fun getAll(): LiveData<List<DatabasePuzzle>>

    @Query("SELECT * FROM puzzle_table WHERE completion_date IS NOT NULL ORDER BY completion_date DESC")
    fun getAllCompleted(): LiveData<List<DatabasePuzzle>>

    @Query("DELETE FROM puzzle_table WHERE completion_date IS NULL")
    suspend fun clearAllUncompleted()

    @Query("DELETE FROM puzzle_table WHERE completion_date IS NOT NULL")
    suspend fun clearAllCompleted()
}
