package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.model.Book
import hu.aut.bme.springbootalf.service.BookService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

@SpringBootTest
class BookControllerTest {

    @InjectMocks
    private lateinit var bookController: BookController

    @Mock
    private lateinit var bookService: BookService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllBooks() {
        val mockBooks = listOf(mock(Book::class.java), mock(Book::class.java))
        `when`(bookService.getAllBooks()).thenReturn(ResponseEntity(mockBooks, HttpStatus.OK))
        val response = bookController.getAllBooks()
        assertTrue(response.body is List<*>)
        assertEquals(2, (response.body as List<*>).size)
    }

    @Test
    fun getBookById() {
        val bookId: Long = 1234
        val mockBook: Book = mock(Book::class.java)
        `when`(bookService.getBookById(bookId)).thenReturn(ResponseEntity(Optional.of(mockBook),HttpStatus.OK))
        val response = bookController.getBookById(bookId)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Optional.of(mockBook), response.body)
    }

    @Test
    fun addBook() {
        val mockBook: Book = mock(Book::class.java)
        `when`(bookService.addBook(mockBook)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(mockBook))
        val response = bookController.addBook(mockBook)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(mockBook, response.body)
    }

    @Test
    fun updateBook() {
        val mockBook: Book = mock(Book::class.java)
        `when`(bookService.updateBook( mockBook)).thenReturn(ResponseEntity.ok(mockBook))
        val response = bookController.updateBook( mockBook)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(mockBook, response.body)
    }

    @Test
    fun deleteBook() {
        val bookId: Long = 1234
        `when`(bookService.deleteBookById(bookId)).thenReturn(ResponseEntity.ok().build())
        val response = bookController.deleteBook(bookId)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}