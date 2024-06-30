package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.dto.UserDTO
import hu.aut.bme.springbootalf.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Controller class for public-facing HTTP requests.
 */
@Validated
@RestController
@RequestMapping("")
class PublicController {

    @Autowired
    private lateinit var userService: UserService

    /**
     * Registers a new user.
     *
     * Endpoint: POST /register
     *
     * @param user The user information to register.
     * @return ResponseEntity containing the status of the registration operation.
     */
    @PostMapping("/register")
    fun register(@RequestBody @Valid user: UserDTO): ResponseEntity<out Any> {
        return userService.createUser(user)
    }
}