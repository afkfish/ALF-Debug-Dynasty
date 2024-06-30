package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.dto.UserDTO
import hu.aut.bme.springbootalf.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity

@SpringBootTest
class PublicControllerTest {

    @InjectMocks
    private lateinit var publicController: PublicController

    @Mock
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun register() {
        val userDto = UserDTO()
        userDto.username = "testUser"
        userDto.password = "testPassword"
        userDto.email = "testEmail"
        `when`(userService.createUser(userDto)).thenReturn(ResponseEntity.ok(userDto))

        val response = publicController.register(userDto)

        assertTrue(response.body is UserDTO)
        assertEquals("testUser", (response.body as UserDTO).username)
        assertEquals("testPassword", (response.body as UserDTO).password)
        assertEquals("testEmail", (response.body as UserDTO).email)
    }
}