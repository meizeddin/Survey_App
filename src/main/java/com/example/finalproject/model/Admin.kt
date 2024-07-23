package com.example.finalproject.model

data class Admin(val id: Int, val departmentId: Int, var fullName: String, var email: String, var key: String, var password: String){
    override fun toString(): String {
        return "Admin(name='$fullName', email=$email)"
    }
}