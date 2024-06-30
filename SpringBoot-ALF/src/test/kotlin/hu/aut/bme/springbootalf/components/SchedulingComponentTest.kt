package hu.aut.bme.springbootalf.components

import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit

@SpringBootTest
class SchedulingComponentTest {
    @InjectMocks
    private lateinit var schedulingComponent: SchedulingComponent

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var emailServiceImpl: EmailServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun remindPasswordChange() {
        // 1. Mock the userRepository to return a list of users
        val testRoles = listOf(UserRole.ROLE_USER)
        val user1 = User(
                "1", "1@test.com", "123", testRoles).apply {
            passwordLastChanged =
                    System.currentTimeMillis() - TimeUnit.DAYS.toMillis(61)
        }
        val user2 = User(
                "2", "2@test.com", "321", testRoles).apply {
            passwordLastChanged =
                    System.currentTimeMillis() - TimeUnit.DAYS.toMillis(62)
        }
        `when`(userRepository.findAll()).thenReturn(listOf(user1, user2))

        // 2. Call the remindPasswordChange method
        schedulingComponent.remindPasswordChange()

        // 3. Verify that the sendSimpleMessage method in emailServiceImpl was called for each user
        verify(emailServiceImpl, times(1)).sendSimpleMessage(user1.email, "Password change reminder", "Please change your password, because it is older than 60 days.")

    }
}