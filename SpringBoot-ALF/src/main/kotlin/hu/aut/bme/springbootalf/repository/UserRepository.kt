package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations on User entities.
 * This interface extends JpaRepository to inherit basic CRUD functionalities.
 */
@Repository
interface UserRepository: JpaRepository<User, String>