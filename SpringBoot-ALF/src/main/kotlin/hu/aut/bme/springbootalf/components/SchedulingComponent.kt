package hu.aut.bme.springbootalf.components

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Component responsible for scheduled tasks.
 */
@Component
class SchedulingComponent {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var emailServiceImpl: EmailServiceImpl

    /**
     * Scheduled task to remind users to change their passwords if they haven't changed them in the last 60 days.
     */
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    fun remindPasswordChange() {

        // Iterate through all users in the repository
        userRepository.findAll().forEach { user ->
            // Check if the user's password was last changed more than 60 days ago
            if (user.passwordLastChanged + TimeUnit.DAYS.toMillis(60) < System.currentTimeMillis()) {
                // Log a warning for the user
                ALFLogger.logWarning("User ${user.username} should change password soon")
                // Send an email reminder to the user
                emailServiceImpl.sendSimpleMessage(user.email, "Password change reminder", "Please change your password, because it is older than 60 days.")
            }
        }
        // Log information about the reminder process
        ALFLogger.logInfo("Reminded users of password change at ${Date()}")
    }
}