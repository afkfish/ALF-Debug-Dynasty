package hu.aut.bme.springbootalf.components

data class PasswordChangeRequest(
    val username: String,
    val oldPassword: String,
    val newPassword: String
)