package com.example.puzzleapp.network

import com.example.puzzleapp.database.DatabasePuzzle

data class PicsumImage(
    var id: Int,
    var author: String,
    var width: Int,
    var height: Int,
    var url: String,
    var download_url: String,
)

fun PicsumImage.asDatabasePuzzle(): DatabasePuzzle {

    return DatabasePuzzle(
        imageUrl = download_url,
        author = author,
    )
}
