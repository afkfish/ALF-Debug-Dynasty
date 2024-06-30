package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.components.PasswordChangeRequest
import hu.aut.bme.springbootalf.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * Controller class for managing user-related operations.
 */
@Validated
@RestController
@RequestMapping("/api/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    /**
     * Retrieves all books owned by the authenticated user.
     *
     * Endpoint: GET /api/user/books
     * Requires: ROLE_USER
     *
     * @param principal The authenticated user.
     * @return ResponseEntity containing the list of user's books or an error message.
     */
    @GetMapping("/books")
    @Secured("ROLE_USER")
    fun getUserBooks(principal: Principal): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request all purchases by user ${principal.name}")
        return userService.getUserBooks(principal.name)
    }

    /**
     * Retrieves the balance of the authenticated user.
     *
     * Endpoint: GET /api/user/balance
     * Requires: ROLE_USER
     *
     * @param principal The authenticated user.
     * @return ResponseEntity containing the user's balance or an error message.
     */
    @GetMapping("/balance")
    @Secured("ROLE_USER")
    fun getUserBalance(principal: Principal): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request balance by user ${principal.name}")
        return userService.getUserBalance(principal.name)
    }

    /**
     * Disables the account of the authenticated user.
     *
     * Endpoint: DELETE /api/user/closeAccount
     * Requires: ROLE_USER
     *
     * @param principal The authenticated user.
     * @return ResponseEntity containing the status of the account closure operation.
     */
    @DeleteMapping("/closeAccount")
    @Secured("ROLE_USER")
    fun disableUser(principal: Principal): ResponseEntity<out Any> {
        // we don't want to delete users, just disable them for reactivation
        return userService.disableUser(principal.name)
    }

    @Secured("ROLE_USER")
    @PostMapping("/changePassword")
    fun changePassword(@RequestBody passwordChangeRequest: PasswordChangeRequest): ResponseEntity<Void> {
        return if (userService.changePassword(passwordChangeRequest).statusCode.is2xxSuccessful) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}