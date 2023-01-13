package com.example.puzzleapp.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PuzzleDatabaseTest {

    private lateinit var puzzleDao: PuzzleDatabaseDao
    private lateinit var db: PuzzleDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PuzzleDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        Timber.d(db.toString())
        puzzleDao = db.puzzleDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPuzzle() = runBlocking {
        puzzleDao.insert(DatabasePuzzle(200L, "testImageUrl", 50))

        val puzzle = puzzleDao.get(200L)
        assertEquals(50, puzzle?.moves)
        assertEquals("testImageUrl", puzzle?.imageUrl)
    }
}
