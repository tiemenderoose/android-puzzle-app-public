package com.example.puzzleapp.domain

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageSplitterTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `split an image into 16 parts`() {
        val splitter = ImageSplitter()

        val parts = splitter
            .addImage(Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888))
            .specifyNrOfTiles(16)
            .split()
            .result()

        assertEquals(16, parts!!.size)
    }
}
