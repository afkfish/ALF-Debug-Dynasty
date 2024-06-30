package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.components.EmailServiceImpl
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ManagementControllerTest {

    @InjectMocks
    private lateinit var managementController: ManagementController

    @Mock
    private lateinit var emailService: EmailServiceImpl

    @Mock
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun sendEmail() {
        doNothing().`when`(emailService).sendSimpleMessage(anyString(), anyString(), anyString())
        managementController.sendEmail("test@example.com", "Test Subject", "Test Message")
        verify(emailService, times(1)).sendSimpleMessage(anyString(), anyString(), anyString())
    }

    @Test
    fun getUsers() {
        `when`(userService.getAllUsers()).thenReturn(ResponseEntity.ok(listOf<User>()))
        val response = managementController.getUsers()
        assertTrue(response.body is List<*>)
        assertEquals(0, (response.body as List<*>).size)
    }

    @Test
    fun reactivateUser() {
        `when`(userService.reactivateUser(anyString())).thenReturn(ResponseEntity.ok("User reactivated"))
        val response = managementController.reactivateUser("testUser")
        assertEquals("User reactivated", response.body)
    }

    @Test
    fun addCredit() {
        `when`(userService.addCredit(anyString(), anyDouble())).thenReturn(ResponseEntity.ok("Credit added"))
        val response = managementController.addCredit("testUser", 100.0)
        assertEquals("Credit added", response.body)
    }
}