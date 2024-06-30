package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.model.Book
import hu.aut.bme.springbootalf.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Service class for handling operations related to Book entities.
 * This class contains methods for CRUD operations on books and provides logging functionality.
 */
@Service
class BookService {
    @Autowired
    // The repository for accessing Book entities.
    private lateinit var bookRepository: BookRepository

    /**
     * Retrieves all books.
     *
     * @return ResponseEntity containing a list of books and HTTP status OK.
     */
    fun getAllBooks(): ResponseEntity<out Any> {
        return ResponseEntity<List<Book>>(bookRepository.findAll().toMutableList(), HttpStatus.OK)
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return ResponseEntity containing the book if found with HTTP status OK,
     * or an error message with HTTP status NOT_FOUND if the book is not found.
     */
    fun getBookById(id: Long): ResponseEntity<out Any> {
        val book = bookRepository.findById(id)
        if(book.isPresent) {
            ALFLogger.logInfo(("Book WAS found with ID: $id"))
            return ResponseEntity<Book>(book.get(), HttpStatus.OK)
        }

        ALFLogger.logError("Book NOT found with ID: $id")
        return ResponseEntity<String>("Book not found with ID: $id", HttpStatus.NOT_FOUND)
    }

    /**
     * Adds a new book.
     *
     * @param book The book to add.
     * @return ResponseEntity containing the added book with HTTP status CREATED,
     * or an error message with HTTP status CONFLICT if the book already exists.
     */
    fun addBook(book: Book): ResponseEntity<out Any> {
        val alreadyExisting = bookRepository.findById(book.id)
        if(alreadyExisting.isPresent) {
            ALFLogger.logWarning("Book already exists with ID: $book.id")
            return ResponseEntity<String>("Book already exists with ID: $book.id", HttpStatus.CONFLICT)
        }
//        if(bookRepository.findById(book.id).isEmpty and (book.author.isBlank() or book.title.isBlank()
//            or book.content.isBlank() or book.price.isInfinite() or book.price.isNaN())) {
//            ALFLogger.logError("Book is not complete at ID: $book.id")
//            return book
//        }
        ALFLogger.logInfo("Book saved with ID: $book.id")
        return ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED)
    }

    /**
     * Updates an existing book.
     *
     * @param book The updated book.
     * @return A ResponseEntity containing the updated book or an appropriate error response.
     */
    fun updateBook(book: Book): ResponseEntity<out Any> {
        val alreadyExisting = bookRepository.findById(book.id)
        if(alreadyExisting.isEmpty) {
            ALFLogger.logWarning("Book is created cause: (it was not present) with ID: $book.id")
            return ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED)
        }

        ALFLogger.logInfo("Book changes was saved ID: $book.id")
        return ResponseEntity<Book>(bookRepository.save(book), HttpStatus.OK)
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @return A ResponseEntity indicating success or failure of the deletion.
     */
    fun deleteBookById(id: Long): ResponseEntity<out Any> {
        if(bookRepository.findById(id).isEmpty) {
            ALFLogger.logError("Book NOT found with ID: $id")
            return ResponseEntity<String>("Book not found with ID: $id", HttpStatus.NOT_FOUND)
        }

        ALFLogger.logInfo("Book found and deleted with ID: $id")
        return ResponseEntity(bookRepository.deleteById(id), HttpStatus.OK)
    }
}