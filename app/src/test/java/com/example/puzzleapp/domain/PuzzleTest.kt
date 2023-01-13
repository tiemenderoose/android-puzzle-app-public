package com.example.puzzleapp.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class PuzzleTest {

    private lateinit var puzzle1: Puzzle
    private lateinit var puzzle2: Puzzle

    @Before
    fun before() {
        puzzle1 = Puzzle(0L, "testurl", 10, null, "auth")
        puzzle2 = Puzzle(1L, "testurl", 10, null, "auth")
    }

    @Test
    fun equals() {
        assertTrue(puzzle1 == puzzle1)
        assertTrue(puzzle1 == puzzle2)

        puzzle2.moves = 9

        assertFalse(puzzle1 == puzzle2)
    }

    @Test
    fun hashcode() {
        assertEquals(puzzle1.hashCode(), puzzle2.hashCode())

        puzzle1.author = "auth1"

        assertNotEquals(puzzle1.hashCode(), puzzle2.hashCode())
    }
}
