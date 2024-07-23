package com.example.finalproject.model

data class Questions(val id: Int, val surveyId: Int, var question: String){
    override fun toString(): String {
        return question
    }
}