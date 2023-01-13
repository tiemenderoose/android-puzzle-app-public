package com.example.puzzleapp.screens.puzzle

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.puzzleapp.repository.IPuzzleRepository

@Suppress("unchecked_cast")
class PuzzleViewModelFactory(private val puzzleId: Long, private val dataSource: IPuzzleRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuzzleViewModel::class.java)) {
            return PuzzleViewModel(puzzleId, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
