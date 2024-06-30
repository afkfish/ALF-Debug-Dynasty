package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.components.EmailServiceImpl
import hu.aut.bme.springbootalf.dto.BookDTO
import hu.aut.bme.springbootalf.dto.UserDTO
import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.components.PasswordChangeRequest
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Service class for user-related operations.
 * This class handles user-related functionalities such as retrieving user books,
 * getting user balance, creating users, disabling users, reactivating users,
 * retrieving all users, and adding credit to users.
 */
@Service
class UserService {

    @Autowired
    // Password encoder for encoding user passwords.
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    // Repository for accessing User entities.
    private lateinit var userRepository: UserRepository

    @Autowired
    // Service for sending emails.
    private lateinit var emailService: EmailServiceImpl

    /**
     * Retrieves the books owned by the user.
     *
     * @param username The username of the user.
     * @return ResponseEntity containing a set of BookDTO objects owned by the user.
     */
    fun getUserBooks(username: String): ResponseEntity<out Any> {
        val user = userRepository.findById(username)
        if (user.isPresent) {
            val books = user.get().books.map { BookDTO(it) }.toSet()
            ALFLogger.logInfo("Book found for user $username")
            return ResponseEntity<Set<BookDTO>>(books, HttpStatus.OK)
        }

        ALFLogger.logError("No user found with $username")
        return ResponseEntity<String>("No user found with $username", HttpStatus.NOT_FOUND)
    }

    /**
     * Retrieves the balance of a user.
     *
     * @param username The username of the user.
     * @return ResponseEntity containing the user's balance.
     */
    fun getUserBalance(username: String): ResponseEntity<out Any> {
        val user = userRepository.findById(username)
        if (user.isPresent){
            val balance = user.get().balance
            ALFLogger.logInfo("User $username found with balance: $balance")
            return ResponseEntity<Double>(balance, HttpStatus.OK)
        }

        ALFLogger.logError("No user found with $username")
        return ResponseEntity<String>("No user found with $username", HttpStatus.NOT_FOUND)
    }

    /**
     * Creates a new user.
     *
     * @param userDTO The DTO containing user information.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    fun createUser(userDTO: UserDTO): ResponseEntity<out Any> {
        if (userRepository.findById(userDTO.username).isPresent){
            ALFLogger.logError("User ${userDTO.username} already exists")
            return ResponseEntity<String>("User ${userDTO.username} already exists", HttpStatus.BAD_REQUEST)
        }

        userRepository.save(createNewUser(userDTO))
        ALFLogger.logInfo("User ${userDTO.username} created")
        emailService.sendSimpleMessage(userDTO.email, "Account created", "Your account has been created at ${System.currentTimeMillis()}")
        return ResponseEntity<String>("User with name \"${userDTO.username}\" created successfully", HttpStatus.CREATED)
    }

    /**
     * Disables a user account.
     *
     * @param username The username of the user to be disabled.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    fun disableUser(username: String): ResponseEntity<out Any> {
        val user = userRepository.findById(username)
        if (user.isPresent){
            user.get().enabled = false
            userRepository.save(user.get())
            ALFLogger.logInfo("User $username disabled")
            emailService.sendSimpleMessage(user.get().email, "Account disabled", "Your account has been deleted at ${System.currentTimeMillis()}")
            return ResponseEntity<String>("User $username deleted", HttpStatus.OK)
        }

        ALFLogger.logError("No user found with $username")
        return ResponseEntity<String>("No user found with $username", HttpStatus.NOT_FOUND)
    }

    /**
     * Reactivates a disabled user account.
     *
     * @param username The username of the user to be reactivated.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    fun reactivateUser(username: String): ResponseEntity<out Any> {
        val user = userRepository.findById(username)
        if (user.isPresent){
            user.get().enabled = true
            userRepository.save(user.get())
            ALFLogger.logInfo("User $username reactivated")
            emailService.sendSimpleMessage(user.get().email, "Account reactivated", "Your account has been reactivated at ${System.currentTimeMillis()}")
            return ResponseEntity<String>("User $username reactivated", HttpStatus.OK)
        }

        ALFLogger.logError("No user found with $username")
        return ResponseEntity<String>("No user found with $username", HttpStatus.NOT_FOUND)
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing the list of all users.
     */
    fun getAllUsers(): ResponseEntity<out Any> {
        val users = userRepository.findAll()
        ALFLogger.logInfo("All users requested")
        return ResponseEntity<List<User>>(users, HttpStatus.OK)
    }

    /**
     * Adds credit to a user's account.
     *
     * @param username The username of the user.
     * @param credit The amount of credit to be added.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    fun addCredit(username: String, credit: Double): ResponseEntity<out Any> {
        val user = userRepository.findById(username)
        if (user.isPresent){
            user.get().balance += credit
            userRepository.save(user.get())
            ALFLogger.logInfo("Credit added to user $username")
            return ResponseEntity<String>("Credit added to user $username", HttpStatus.OK)
        }

        ALFLogger.logError("No user found with $username")
        return ResponseEntity<String>("No user found with $username", HttpStatus.NOT_FOUND)
    }

    /**
     * Creates a new User entity based on the provided DTO.
     *
     * @param userDTO The DTO containing user information.
     * @return The newly created User entity.
     */
    fun createNewUser(userDTO: UserDTO): User {
        val user = User(
            username = userDTO.username,
            email = userDTO.email,
            password = passwordEncoder.encode(userDTO.password),
            roles = listOf(UserRole.ROLE_USER)
        )
        return user
    }
    fun changePassword(request: PasswordChangeRequest) : ResponseEntity<out Any>{
        val user = userRepository.findById(request.username)
        if (user.isPresent && passwordEncoder.matches(request.oldPassword, user.get().password)) {
            user.get().password = passwordEncoder.encode(request.newPassword)
            userRepository.save(user.get())
            ALFLogger.logInfo("Password changed for user ${request.username}")
            return ResponseEntity("Password changed for user ${request.username}", HttpStatus.OK)
        }
        else if (user.isPresent) {
            ALFLogger.logError("Old password is incorrect")
            return ResponseEntity("Password Incorrect", HttpStatus.FORBIDDEN)
        }
        else {
            ALFLogger.logError("No user found with ${request.username}")
            return ResponseEntity("No user found with ${request.username}", HttpStatus.NOT_FOUND)
        }
    }
}