package hu.aut.bme.springbootalf.configuration

import hu.aut.bme.springbootalf.enums.Genre
import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.model.Book
import hu.aut.bme.springbootalf.model.User
import hu.aut.bme.springbootalf.repository.BookRepository
import hu.aut.bme.springbootalf.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Configuration class responsible for initializing the database with sample data.
 */
@Configuration
class LoadDatabase {

    private val log = LoggerFactory.getLogger(LoadDatabase::class.java)

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    /**
     * Initializes the database with sample data using a CommandLineRunner.
     *
     * This method populates the database with sample user and book data upon application startup.
     * It retrieves byte content from a PDF file to store in the Book entities.
     *
     * @param userRepository The repository for managing users.
     * @param bookRepository The repository for managing books.
     * @return A CommandLineRunner that populates the database with sample data.
     */
    @Bean
    fun initDatabase(userRepository: UserRepository, bookRepository: BookRepository): CommandLineRunner {
        // Read byte content from a PDF file
        val byteContent = ClassPathResource("demo.pdf").contentAsByteArray
        return CommandLineRunner {
            log.info("Preloading staring...")

            // Save sample users to the user repository
            userRepository.save(User("admin", "admin@admin.com", passwordEncoder.encode("admin"), listOf(UserRole.ROLE_ADMIN, UserRole.ROLE_USER)))
            userRepository.save(User("user", "user@user.com", passwordEncoder.encode("user"), listOf(UserRole.ROLE_USER)))

            // Save sample books to the book repository
            bookRepository.save(Book("The Lord of the Rings", "J.R.R. Tolkien", "2000", setOf(Genre.FANTASY), byteContent, 29.99))
            bookRepository.save(Book("The Hobbit", "J.R.R. Tolkien", "2000", setOf(Genre.FANTASY), byteContent, 19.99))
            log.info("Preloading completed")
        }
    }
}