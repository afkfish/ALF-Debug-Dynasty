package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.components.EmailServiceImpl
import hu.aut.bme.springbootalf.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Controller class for management-related HTTP requests.
 * Endpoints are secured based on user roles.
 */
@Validated
@RestController
@RequestMapping("/api/management")
class ManagementController {

    @Autowired
    private lateinit var emailService: EmailServiceImpl

    @Autowired
    private lateinit var userService: UserService

    /**
     * Sends an email.
     *
     * Endpoint: GET /api/management/sendEmail
     * Requires: ROLE_ADMIN
     *
     * @param to The recipient email address.
     * @param subject The email subject.
     * @param text The email content.
     * @return ResponseEntity containing the status of the email sending operation.
     */
    @GetMapping("/sendEmail")
    @Secured("ROLE_ADMIN")
    fun sendEmail(@RequestParam to: String, @RequestParam subject: String, @RequestParam text: String): Any {
        return emailService.sendSimpleMessage(to, subject, text)
    }

    /**
     * Retrieves all users.
     *
     * Endpoint: GET /api/management/users
     * Requires: ROLE_ADMIN
     *
     * @return ResponseEntity containing a list of users or an error message.
     */
    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    fun getUsers(): ResponseEntity<out Any> {
        return userService.getAllUsers()
    }

    /**
     * Reactivates a user account.
     *
     * Endpoint: POST /api/management/reactivateUser
     * Requires: ROLE_ADMIN
     *
     * @param username The username of the user to reactivate.
     * @return ResponseEntity indicating success or failure of the reactivation.
     */
    @PostMapping("/reactivateUser")
    @Secured("ROLE_ADMIN")
    fun reactivateUser(@RequestParam username: String): ResponseEntity<out Any> {
        return userService.reactivateUser(username)
    }

    /**
     * Adds credit to a user's account.
     *
     * Endpoint: GET /api/management/addCredit
     * Requires: ROLE_ADMIN
     *
     * @param username The username of the user to add credit to.
     * @param credit The amount of credit to add.
     * @return ResponseEntity indicating success or failure of the credit addition.
     */
    @GetMapping("/addCredit")
    @Secured("ROLE_ADMIN")
    fun addCredit(@RequestParam username: String, @RequestParam credit: Double): ResponseEntity<out Any> {
        return userService.addCredit(username, credit)
    }
}