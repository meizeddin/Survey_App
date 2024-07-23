package com.example.finalproject.model

data class Answers(val id: Int, var answer: String){
    override fun toString(): String {
        return this.answer
    }
}