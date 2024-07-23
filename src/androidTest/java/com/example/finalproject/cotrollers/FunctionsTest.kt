package com.example.finalproject.cotrollers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test

class FunctionsTest {
    private lateinit var functions: Functions

    @Test
    fun checkStudent() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        functions = Functions(context)
        val studentEmail = "moha12@gmail.com"
        val pass = "M123"
        assertEquals(true, functions.checkStudent(studentEmail, pass))

    }

    @Test
    fun checkAdmin() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        functions = Functions(context)
        val adminEmail = "ali12@gmail.com"
        val pass = "V123"
        assertEquals(false, functions.checkAdmin(adminEmail, pass))
    }
}