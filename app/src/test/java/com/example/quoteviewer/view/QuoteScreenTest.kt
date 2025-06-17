package com.example.quoteviewer.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class QuoteScreenTest {

    @Test
    fun `placeholder`() = runTest{
        assertEquals(2,2)
    }
}