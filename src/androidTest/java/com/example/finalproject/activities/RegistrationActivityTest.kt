package com.example.finalproject.activities

import android.os.Handler
import android.os.Looper
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RegistrationActivityTest {
    @get: Rule
    val activityScenarioRule = activityScenarioRule<RegistrationActivity>()

    @Test
    fun validateRegistrationInput() {
        val activity = RegistrationActivity()
        val studentDep = 1
        val studentName = "Ahmed"
        val email = "ahmed12@gmail.com"
        val pass = "A123"
        val result = activity.validateRegistrationInput(
            studentDep,
            studentName,
            email,
            pass)
        assertEquals(true, result)
    }
}