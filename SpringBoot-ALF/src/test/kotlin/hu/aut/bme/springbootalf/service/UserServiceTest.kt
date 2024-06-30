package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.components.EmailServiceImpl
import hu.aut.bme.springbootalf.components.PasswordChangeRequest
import hu.aut.bme.springbootalf.dto.UserDTO
import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var emailService: EmailServiceImpl

    private lateinit var userDTO: UserDTO
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userDTO= UserDTO()
        userDTO.username = "testUser"
        userDTO.password = "testPassword"
        userDTO.email = "testEmail"
    }

    private val user = User("testUser", "testEmail", "testPassword", listOf(UserRole.ROLE_USER))

    @Test
    fun getUserBooks() {
        val user = user
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        var response = userService.getUserBooks("testUser")
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun getUserBooksNotFound(){
        val response = userService.getUserBooks("MrNegative")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun getUserBalance() {
        val user = user
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        var response = userService.getUserBalance("testUser")
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun getUserBalanceNotFound(){
        val response = userService.getUserBalance("MrNegative")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun createUser() {
        `when`(userRepository.findById("testUser")).thenReturn(Optional.empty())
        `when`(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword")
        var response = userService.createUser(userDTO)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun userAlreadyExists() {
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        val response = userService.createUser(userDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun disableUser() {
        val user = user
        user.email="testEmail@test.test"
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        var response = userService.disableUser("testUser")
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun disableUserNotFound(){
        val response = userService.disableUser("MrNegative")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun reactivateUser() {
        val user = user
        user.email="test@test.test"
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        var response = userService.reactivateUser("testUser")
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun reactivateUserNotFound(){
        val response = userService.reactivateUser("MrNegative")
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun getAllUsers() {
        `when`(userRepository.findAll()).thenReturn(listOf())
        val response = userService.getAllUsers()
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun addCredit() {
        val user = user
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        var response = userService.addCredit("testUser", 100.0)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun addCreditNotFound(){
        val response = userService.addCredit("MrNegative", 10.0)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun changePassword() {
        val user = user
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches("testPassword", "testPassword")).thenReturn(true)
        `when`(passwordEncoder.encode( anyString())).thenReturn("encodedPassword")
        var response = userService.changePassword(PasswordChangeRequest("testUser", "testPassword", "newPassword"))
        assertEquals(HttpStatus.OK, response.statusCode)
    }
    @Test
    fun changePasswordNotFound(){
        val response = userService.changePassword(PasswordChangeRequest("MrNegative", "testPassword", "newPassword"))
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
    @Test
    fun changePasswordOldPasswordNotKnown(){
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        `when`(passwordEncoder.matches("wrongPassword", "testPassword")).thenReturn(false)
        val response = userService.changePassword(PasswordChangeRequest("testUser", "wrongPassword", "newPassword"))
        assertEquals(HttpStatus.FORBIDDEN, response.statusCode)
    }
}