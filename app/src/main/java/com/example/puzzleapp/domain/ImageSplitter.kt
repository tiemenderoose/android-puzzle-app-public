package com.example.puzzleapp.domain

import android.graphics.Bitmap
import kotlin.math.sqrt

class ImageSplitter {

    private lateinit var bitmap: Bitmap
    private var nrOfTiles: Int = 16
    private var tiles: MutableList<Bitmap>? = null

    fun addImage(bitmap: Bitmap): ImageSplitter {
        this.bitmap = bitmap
        return this
    }

    fun specifyNrOfTiles(nrOfTiles: Int): ImageSplitter {
        this.nrOfTiles = nrOfTiles
        return this
    }

    fun split(): ImageSplitter {
        createTiles(bitmap)
        return this
    }

    fun shuffleTiles(order: MutableList<Int>): ImageSplitter {
        if (tiles != null) {
            val shuffledTiles = mutableListOf<Bitmap>()
            order.forEach { index ->
                shuffledTiles.add(tiles!![index])
            }
            tiles = shuffledTiles
        }
        return this
    }

    fun result(): List<Bitmap>? {
        return tiles?.toList()
    }

    private fun createTiles(bitmap: Bitmap) {
        val nrOfRowsAndCols = sqrt(nrOfTiles.toDouble()).toInt()
        val tileHeight = bitmap.height / nrOfRowsAndCols
        val tileWidth = bitmap.width / nrOfRowsAndCols

        tiles = mutableListOf()

        // x and y are the coordinates of the corner of a tile
        // from these coordinates, they will cut out the specified tileWidth and tileHeight,
        // which is turned into a separate bitmap
        var posY = 0
        for (y in 0 until nrOfRowsAndCols) {
            var posX = 0
            for (x in 0 until nrOfRowsAndCols) {
                tiles!!.add(Bitmap.createBitmap(bitmap, posX, posY, tileWidth, tileHeight))
                posX += tileWidth
            }
            posY += tileHeight
        }
    }
}
