package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations on Book entities.
 * This interface extends JpaRepository to inherit basic CRUD functionalities.
 */
@Repository
interface BookRepository: JpaRepository<Book, Long>