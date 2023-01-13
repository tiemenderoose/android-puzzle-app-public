package com.example.puzzleapp.screens.puzzle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.MockPuzzleRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PuzzleViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepo: MockPuzzleRepository
    private lateinit var viewModel: PuzzleViewModel

    @Before
    fun before() {
        mockRepo = MockPuzzleRepository(
            listOf(
                Puzzle(
                    0L,
                    "https://picsum.photos/id/450/800",
                )
            )
        )
        viewModel = PuzzleViewModel(0L, mockRepo, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun selectImage() {
        assertEquals(null, viewModel.selectedImage.value)

        viewModel.selectImage(3)
        assertEquals(3, viewModel.selectedImage.value)
    }
}
