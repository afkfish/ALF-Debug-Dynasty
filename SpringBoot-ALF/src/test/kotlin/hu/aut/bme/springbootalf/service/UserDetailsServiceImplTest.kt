package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UserDetailsServiceImplTest {

    @InjectMocks
    private lateinit var userDetailsService: UserDetailsServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun loadUserByUsername() {
        val user = User("testUser", "asd@asd.com", "password", listOf(UserRole.ROLE_USER))
        `when`(userRepository.findById("testUser")).thenReturn(Optional.of(user))
        val userDetails = userDetailsService.loadUserByUsername("testUser")
        if (userDetails != null) {
            assertEquals(user.username, userDetails.username)
        }
        `when`(userRepository.findById("testUser")).thenReturn(Optional.empty())
        try {
            userDetailsService.loadUserByUsername("testUser")
        } catch (e: Exception) {
            assertEquals("User not found with username: testUser", e.message)
        }
    }
}