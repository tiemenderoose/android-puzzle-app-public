package com.example.puzzleapp.domain

import com.example.puzzleapp.database.DatabasePuzzle
import java.util.Objects

class Puzzle(
    var puzzleId: Long = 0L,
    var imageUrl: String = "",
    var moves: Int = 0,
    var completionDate: String? = null,
    var author: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Puzzle

        if (imageUrl != other.imageUrl) return false
        if (moves != other.moves) return false
        if (completionDate != other.completionDate) return false
        if (author != other.author) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(imageUrl, moves, completionDate, author)
    }
}

fun Puzzle.asDatabasePuzzle(): DatabasePuzzle {
    return DatabasePuzzle(
        puzzleId,
        imageUrl,
        moves,
        completionDate,
        author,
    )
}
