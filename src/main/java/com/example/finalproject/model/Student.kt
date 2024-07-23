package com.example.finalproject.model

data class Student (val id: Int, val departmentId: Int, var fullName: String, var email: String, var password: String){
    override fun toString(): String {
        return "Student(name='$fullName', email=$email)"
    }
}