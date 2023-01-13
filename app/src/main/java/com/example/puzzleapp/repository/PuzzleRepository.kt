package com.example.puzzleapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.puzzleapp.database.PuzzleDatabase
import com.example.puzzleapp.database.asDomainModel
import com.example.puzzleapp.domain.Puzzle
import com.example.puzzleapp.domain.asDatabasePuzzle
import com.example.puzzleapp.network.ImageApiService
import com.example.puzzleapp.network.asDatabasePuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

class PuzzleRepository(private val database: PuzzleDatabase) : IPuzzleRepository {

    private val logger = Timber.tag("PuzzleRepository")

    override val puzzles: LiveData<List<Puzzle>> =
        Transformations.map(database.puzzleDatabaseDao.getAll()) {
            it.asDomainModel()
        }
    override val completedPuzzles: LiveData<List<Puzzle>> =
        Transformations.map(database.puzzleDatabaseDao.getAllCompleted()) {
            it.asDomainModel()
        }

    override suspend fun createNewPuzzle(): Boolean {
        return withContext(Dispatchers.IO) {
            var success = false

            // fetch a new picsum image from the api
            val image = ImageApiService.INSTANCE.getNewImageAsync(Random.nextInt(1000))

            try {
                val result = image.await()

                // format the received image url to something we can use in the app
                val lastIndex: Int = result.download_url.lastIndexOf('/')
                if (lastIndex > -1) {
                    val cutoffPoint: Int = result.download_url.lastIndexOf('/', lastIndex - 1)
                    result.download_url = result.download_url.substring(0, cutoffPoint) + "/800"
                }

                // add the puzzle to the database
                database.puzzleDatabaseDao.insert(result.asDatabasePuzzle())

                success = true
            } catch (ex: Exception) {
                logger.e("Could not fetch a new image url from API")
                logger.e(ex.printStackTrace().toString())
            }

            // return if operation was successful
            success
        }
    }

    override suspend fun updatePuzzle(puzzle: Puzzle) {
        withContext(Dispatchers.IO) {
            database.puzzleDatabaseDao.update(puzzle.asDatabasePuzzle())
        }
    }

    override suspend fun getPuzzleById(id: Long): Puzzle? {
        return withContext(Dispatchers.IO) {
            database.puzzleDatabaseDao.get(id)?.asDomainModel()
        }
    }

    override suspend fun clearAllCompletedPuzzles() {
        withContext(Dispatchers.IO) {
            database.puzzleDatabaseDao.clearAllCompleted()
        }
    }

    override suspend fun clearAllUncompletedPuzzles() {
        withContext(Dispatchers.IO) {
            database.puzzleDatabaseDao.clearAllUncompleted()
        }
    }

    companion object {

        var instance: PuzzleRepository? = null

        fun getInstance(appContext: Application): PuzzleRepository {

            if (instance == null) {
                instance = PuzzleRepository(PuzzleDatabase.getInstance(appContext))
            }

            return instance!!
        }
    }
}
