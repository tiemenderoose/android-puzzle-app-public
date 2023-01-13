package com.example.puzzleapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class PuzzleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // initialize datetime library
        AndroidThreeTen.init(this)
    }
}
