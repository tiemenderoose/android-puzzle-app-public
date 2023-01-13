package com.example.puzzleapp.repository

import androidx.lifecycle.LiveData
import com.example.puzzleapp.domain.Puzzle

interface IPuzzleRepository {

    val puzzles: LiveData<List<Puzzle>>
    val completedPuzzles: LiveData<List<Puzzle>>

    suspend fun createNewPuzzle(): Boolean

    suspend fun updatePuzzle(puzzle: Puzzle)

    suspend fun getPuzzleById(id: Long): Puzzle?

    suspend fun clearAllCompletedPuzzles()

    suspend fun clearAllUncompletedPuzzles()
}
