package com.example.finalproject.model

data class StudentProgress (val id: Int, val studentId: Int, val surveyId: Int, var progress: Int, var array: String){
    override fun toString(): String {
        return ""
    }
}