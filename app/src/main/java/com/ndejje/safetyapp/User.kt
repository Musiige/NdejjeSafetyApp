package com.ndejje.safetyapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val fullName: String,
    val username: String,
    val email: String,
    val passwordHash: String ,   // store hashed, never plain text
    val role: String = "student" // New field: default is "student"
)
