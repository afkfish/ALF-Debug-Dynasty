package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.model.UserDetailsImpl
import hu.aut.bme.springbootalf.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Service class for loading user details.
 * This class implements the UserDetailsService interface provided by Spring Security
 * to retrieve user details from the database.
 */
@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    /**
     * Loads user details by username.
     *
     * @param username The username of the user whose details to load.
     * @return UserDetailsImpl object containing the user details.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    override fun loadUserByUsername(username: String?): UserDetails? {
        val user = userRepository.findById(username ?: "")

        if (user.isEmpty) {
            throw UsernameNotFoundException("User not found with username: $username")
        }
        else {
            return UserDetailsImpl(user.get())
        }
    }
}