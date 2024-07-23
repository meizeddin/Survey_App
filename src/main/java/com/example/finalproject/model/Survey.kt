package com.example.finalproject.model

import java.io.Serializable

data class Survey (val id: Int, val departmentId: Int, var title: String, var startDate: String, var endDate: String, var isPublished: Boolean):
    Serializable {
    override fun toString(): String {
        return title
    }
}