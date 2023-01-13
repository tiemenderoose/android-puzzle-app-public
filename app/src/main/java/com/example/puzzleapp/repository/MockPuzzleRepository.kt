package com.example.puzzleapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.puzzleapp.domain.Puzzle

class MockPuzzleRepository(
    puzzles: List<Puzzle> = mutableListOf(),
    completedPuzzles: List<Puzzle> = mutableListOf(),
) : IPuzzleRepository {

    private val _puzzles = MutableLiveData<List<Puzzle>>()
    override val puzzles: LiveData<List<Puzzle>>
        get() = _puzzles

    private val _completedPuzzles = MutableLiveData<List<Puzzle>>()
    override val completedPuzzles: LiveData<List<Puzzle>>
        get() = _completedPuzzles

    init {
        _puzzles.value = puzzles
        _completedPuzzles.value = completedPuzzles
    }

    override suspend fun createNewPuzzle(): Boolean {
        val mutableList = _puzzles.value?.toMutableList() ?: mutableListOf()
        mutableList.add(Puzzle())
        _puzzles.value = mutableList.toList()
        return true
    }

    override suspend fun updatePuzzle(puzzle: Puzzle) {
        val mutableList = _puzzles.value!!.toMutableList()
        val index = _puzzles.value!!.indexOf(puzzle)
        mutableList[index] = puzzle
        _puzzles.value = mutableList.toList()
    }

    override suspend fun getPuzzleById(id: Long): Puzzle? {
        return _puzzles.value!!.find { p -> p.puzzleId == id }
    }

    override suspend fun clearAllCompletedPuzzles() {
        _completedPuzzles.value = listOf()
    }

    override suspend fun clearAllUncompletedPuzzles() {
        val mutableList = _puzzles.value!!.toMutableList()
        mutableList.removeIf { p -> !_completedPuzzles.value!!.contains(p) }
        _puzzles.value = mutableList.toList()
    }
}
