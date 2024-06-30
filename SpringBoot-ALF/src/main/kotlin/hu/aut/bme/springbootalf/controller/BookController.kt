package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.model.Book
import hu.aut.bme.springbootalf.service.BookService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Controller class for managing book-related HTTP requests.
 * Endpoints are secured based on user roles.
 */
@Validated
@RestController
@RequestMapping("/api/book")
class BookController {

    @Autowired
    private lateinit var bookService: BookService

    /**
     * Retrieves all books.
     *
     * Endpoint: GET /api/book
     * Requires: ROLE_USER
     *
     * @return ResponseEntity containing a list of books or an error message.
     */
    @GetMapping("")
    @Secured("ROLE_USER")
    fun getAllBooks(): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request all books by a user")
        return bookService.getAllBooks()
    }

    /**
     * Retrieves a book by its ID.
     *
     * Endpoint: GET /api/book/{bookId}
     * Requires: ROLE_USER
     *
     * @param bookId The ID of the book to retrieve.
     * @return ResponseEntity containing the book or an error message.
     */
    @GetMapping("/{bookId}")
    @Secured("ROLE_USER")
    fun getBookById(@PathVariable bookId: Long): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request book where id = $bookId by a user")
        return bookService.getBookById(bookId)
    }

    /**
     * Adds a new book.
     *
     * Endpoint: POST /api/book
     * Requires: ROLE_ADMIN
     *
     * @param book The book to add.
     * @return ResponseEntity containing the added book or an error message.
     */
    @PostMapping("")
    @Secured("ROLE_ADMIN")
    fun addBook(@RequestBody @Valid book: Book): ResponseEntity<out Any> {
        ALFLogger.logDebug("POST request book = $book by admin")
        return bookService.addBook(book)
    }

    /**
     * Updates an existing book.
     *
     * Endpoint: PUT /api/book/update
     * Requires: ROLE_ADMIN
     *
     * @param book The updated book.
     * @return ResponseEntity containing the updated book or an error message.
     */
    @PutMapping("/update")
    @Secured("ROLE_ADMIN")
    fun updateBook(@RequestBody @Valid book: Book): ResponseEntity<out Any> {
        ALFLogger.logDebug("PUT request book = $book by admin")
        return bookService.updateBook(book)
    }

    /**
     * Deletes a book by its ID.
     *
     * Endpoint: DELETE /api/book/{bookId}
     * Requires: ROLE_ADMIN
     *
     * @param bookId The ID of the book to delete.
     * @return ResponseEntity indicating success or failure of the deletion.
     */
    @DeleteMapping("/{bookId}")
    @Secured("ROLE_ADMIN")
    fun deleteBook(@PathVariable bookId: Long): ResponseEntity<out Any> {
        ALFLogger.logDebug("DELETE request book where id = $bookId by admin")
        return bookService.deleteBookById(bookId)
    }
}