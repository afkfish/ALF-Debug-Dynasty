package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.components.PasswordChangeRequest
import hu.aut.bme.springbootalf.service.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import java.security.Principal

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private lateinit var userController: UserController

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var principal: Principal

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getUserBooks() {
        `when`(principal.name).thenReturn("testUser")
        `when`(userService.getUserBooks("testUser")).thenReturn(ResponseEntity.ok().build())
        val response = userController.getUserBooks(principal)
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun getUserBalance() {
        `when`(principal.name).thenReturn("testUser")
        `when`(userService.getUserBalance("testUser")).thenReturn(ResponseEntity.ok().build())
        val response = userController.getUserBalance(principal)
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun disableUser() {
        `when`(principal.name).thenReturn("testUser")
        `when`(userService.disableUser("testUser")).thenReturn(ResponseEntity.ok().build())
        val response = userController.disableUser(principal)
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun changePassword() {
        `when`(principal.name).thenReturn("testUser")
        val request = PasswordChangeRequest("testUser", "oldPassword", "newPassword")
        `when`(userService.changePassword(request)).thenReturn(ResponseEntity.ok().build())
        val response = userController.changePassword(request)
        assertTrue(response.statusCode.is2xxSuccessful)
    }
}