package com.example.puzzleapp.screens.puzzlePicker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.puzzleapp.repository.IPuzzleRepository

@Suppress("unchecked_cast")
class PuzzlePickerViewModelFactory(private val dataSource: IPuzzleRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuzzlePickerViewModel::class.java)) {
            return PuzzlePickerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
