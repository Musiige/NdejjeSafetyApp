package com.ndejje.safetyapp

class UserRepository(private val userDao: UserDao) {

    // Simple hash for demonstration
    private fun hashPassword(password: String): String =
        password.hashCode().toString()

    // Returns true if registration succeeded, false if username is taken
    suspend fun registerUser(
        fullName: String, username: String,
        email: String,    password: String
    ): Boolean {
        if (userDao.findByUsername(username) != null) return false
        val rowId = userDao.insertUser(
            UserEntity(fullName = fullName, username = username,
                email = email, passwordHash = hashPassword(password))
        )
        return rowId != -1L
    }

    // Returns the UserEntity if credentials are valid, null if not
    suspend fun loginUser(username: String, password: String): UserEntity? =
        userDao.login(username = username, passwordHash = hashPassword(password))
}