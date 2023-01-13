package com.example.puzzleapp.screens.puzzleComplete

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.IPuzzleRepository
import kotlinx.coroutines.launch

class PuzzleCompleteViewModel(private val puzzleId: Long, val dataSource: IPuzzleRepository, val application: Application) : ViewModel() {

    private val _puzzle = MutableLiveData<Puzzle?>()
    val puzzle: LiveData<Puzzle?>
        get() = _puzzle

    init {
        viewModelScope.launch {
            _puzzle.value = dataSource.getPuzzleById(puzzleId)
        }
    }
}
