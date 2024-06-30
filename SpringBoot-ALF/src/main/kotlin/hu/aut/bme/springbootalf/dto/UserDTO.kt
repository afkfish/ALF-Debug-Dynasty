package hu.aut.bme.springbootalf.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Data Transfer Object (DTO) representing a User.
 * This class is used for transferring User data between layers of the application.
 */
class UserDTO {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 20 characters")
    // The username of the user.
    lateinit var username: String

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    // The email address of the user.
    lateinit var email: String

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    // The password of the user.
    lateinit var password: String
    // The list of book titles associated with the user.
    var books: List<String>? = null

    // The balance of the user.
    var balance: Double = 0.0
}