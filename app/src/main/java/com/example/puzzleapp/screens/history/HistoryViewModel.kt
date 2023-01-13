package com.example.puzzleapp.screens.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.IPuzzleRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val dataSource: IPuzzleRepository, val application: Application) : ViewModel() {

    private val _puzzleList = dataSource.completedPuzzles
    val puzzleList: LiveData<List<Puzzle>>
        get() = _puzzleList

    fun clearHistory() {
        viewModelScope.launch {
            dataSource.clearAllCompletedPuzzles()
        }
    }
}
