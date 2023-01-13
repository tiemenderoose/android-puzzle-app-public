package com.example.puzzleapp.screens.puzzlePicker

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.IPuzzleRepository
import kotlinx.coroutines.launch

class PuzzlePickerViewModel(val dataSource: IPuzzleRepository, val application: Application) : ViewModel() {

    private val _puzzleList = dataSource.puzzles
    val puzzleList: LiveData<List<Puzzle>>
        get() = _puzzleList

    private val _requestFailed = MutableLiveData(false)
    val requestFailed: LiveData<Boolean>
        get() = _requestFailed

    fun addPuzzle() {
        viewModelScope.launch {
            _requestFailed.value = !dataSource.createNewPuzzle()
        }
    }

    fun clearUnfinishedPuzzles() {
        viewModelScope.launch {
            dataSource.clearAllUncompletedPuzzles()
        }
    }

    fun showSnackbarComplete() {
        _requestFailed.value = false
    }
}
