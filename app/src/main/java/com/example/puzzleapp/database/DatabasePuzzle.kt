package com.example.puzzleapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.puzzleapp.domain.Puzzle

@Entity(tableName = "puzzle_table")
data class DatabasePuzzle(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "puzzle_id")
    var puzzleId: Long = 0L,

    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",

    // number of moves it took to complete the puzzle
    @ColumnInfo(name = "moves")
    var moves: Int = 0,

    // null means the puzzle was never completed
    @ColumnInfo(name = "completion_date")
    var completionDate: String? = null,

    // author of the image, provided by api
    @ColumnInfo(name = "author")
    var author: String = ""
)

fun DatabasePuzzle.asDomainModel(): Puzzle {
    return Puzzle(
        puzzleId,
        imageUrl,
        moves,
        completionDate,
        author,
    )
}

fun List<DatabasePuzzle>.asDomainModel(): List<Puzzle> {
    return map {
        Puzzle(
            puzzleId = it.puzzleId,
            imageUrl = it.imageUrl,
            moves = it.moves,
            completionDate = it.completionDate,
            author = it.author,
        )
    }
}
