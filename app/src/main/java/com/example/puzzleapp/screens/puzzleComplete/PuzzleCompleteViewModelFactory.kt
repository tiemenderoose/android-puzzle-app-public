package com.example.puzzleapp.screens.puzzleComplete

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.puzzleapp.repository.IPuzzleRepository

@Suppress("unchecked_cast")
class PuzzleCompleteViewModelFactory(private val puzzleId: Long, private val dataSource: IPuzzleRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuzzleCompleteViewModel::class.java)) {
            return PuzzleCompleteViewModel(puzzleId, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
