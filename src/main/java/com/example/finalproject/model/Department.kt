package com.example.finalproject.model


data class Department (val id: Int, var title: String){
    override fun toString(): String {
        return title
    }
}