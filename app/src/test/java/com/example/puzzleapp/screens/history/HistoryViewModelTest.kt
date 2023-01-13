package com.example.puzzleapp.screens.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.repository.MockPuzzleRepository
import getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockRepo: MockPuzzleRepository
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun before() {
        mockRepo = MockPuzzleRepository(
            completedPuzzles = listOf(
                Puzzle(
                    0L,
                    "https://picsum.photos/id/450/800",
                )
            )
        )
        viewModel = HistoryViewModel(mockRepo, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun clearHistory() {
        assertEquals(1, viewModel.puzzleList.getOrAwaitValue { }.size)
        viewModel.clearHistory()
        assertEquals(0, viewModel.puzzleList.getOrAwaitValue { }.size)
    }
}
