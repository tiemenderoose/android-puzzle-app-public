package com.example.puzzleapp.screens.puzzle

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.puzzleapp.domain.ImageSplitter
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.IPuzzleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import java.util.Collections

class PuzzleViewModel(private val puzzleId: Long, val dataSource: IPuzzleRepository, val application: Application) : ViewModel() {

    private val NR_OF_TILES = 16
    lateinit var puzzle: Puzzle
    var moves = 0

    // the order which we will apply to the created tiles
    private var order = (0 until NR_OF_TILES).toList().shuffled().toMutableList()

    private val _imageList = MutableLiveData<MutableList<Bitmap>>()
    val imageList: LiveData<MutableList<Bitmap>>
        get() = _imageList

    // keeps track which index in the image list is currently selected
    private val _selectedImage = MutableLiveData<Int?>()
    val selectedImage: LiveData<Int?>
        get() = _selectedImage

    // becomes true when the view-model determines that the puzzle was completed
    private val _isFinished = MutableLiveData(false)
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    init {
        loadPuzzle()
    }

    private fun loadPuzzle() {
        viewModelScope.launch {
            puzzle = dataSource.getPuzzleById(puzzleId)!!

            var bitmap: Bitmap

            withContext(Dispatchers.IO) {
                bitmap = Glide.with(application).asBitmap().load(puzzle.imageUrl).submit(800, 800).get()
            }

            _imageList.value = ImageSplitter()
                .addImage(bitmap)
                .specifyNrOfTiles(NR_OF_TILES)
                .split()
                .shuffleTiles(order)
                .result()!!.toMutableList()
        }
    }

    // uses the index of the selected image from PuzzleFragment and saves it
    // if an index is already saved, swap these two indexes instead and allow for a new selection
    fun selectImage(nr: Int) {
        if (_selectedImage.value == null) {
            _selectedImage.value = nr
        } else {
            if (_selectedImage.value != nr) {
                swapImage(nr, _selectedImage.value!!)
            }
            _selectedImage.value = null
        }
    }

    // called when the image needs to be deselected
    fun deselectImage() {
        _selectedImage.value = null
    }

    // swaps the two images, also start a check to see if the game has resolved
    private fun swapImage(id1: Int, id2: Int) {
        Collections.swap(order, id1, id2)
        Collections.swap(_imageList.value!!, id1, id2)
        moves += 1
        _imageList.notifyObserver()
        gameFinishedCheck()
    }

    // check if the order array is in the right order (ascending from 0 to n)
    // if true set isFinished to true and update puzzle data
    private fun gameFinishedCheck() {
        var previous = -1
        var isCorrectOrder = true

        for (element in order) {
            if (previous + 1 != element) {
                isCorrectOrder = false
                break
            }
            previous = element
        }

        if (isCorrectOrder) {
            puzzle.moves = moves
            puzzle.completionDate = LocalDateTime.now().toString()

            viewModelScope.launch {
                dataSource.updatePuzzle(puzzle)
            }
        }

        _isFinished.value = isCorrectOrder
    }

    // extension function for notifying the list observers
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}
