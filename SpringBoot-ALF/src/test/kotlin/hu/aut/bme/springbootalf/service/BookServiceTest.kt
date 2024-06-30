package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.model.Book
import hu.aut.bme.springbootalf.repository.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest
class BookServiceTest {

    @InjectMocks
    private lateinit var bookService: BookService

    @Mock
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setUp() {

        MockitoAnnotations.openMocks(this)

    }

    @Test
    fun getAllBooks() {
        val books = listOf(Book(1L), Book(1L), Book(1L))
        `when`(bookRepository.findAll()).thenReturn(books)
        val result = bookService.getAllBooks()
        assertEquals(3, (result.body as List<*>).size)
    }

    @Test
    fun getBookById() {
        val book = Book(1L)
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(book))
        val result = bookService.getBookById(1L)
        assertEquals(book, result.body)
    }

    @Test
    fun getBookByIdNotFound() {
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.empty())
        val result = bookService.getBookById(2L)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }


    @Test
    fun addBook() {
        val book = Book(1L)
        `when`(bookRepository.save(any(Book::class.java))).thenReturn(book)
        val result = bookService.addBook(book)
        assertEquals(book, result.body)
    }

    @Test
    fun addBookAlreafyExists() {
        val book = Book(1L)
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(book))
        val result = bookService.addBook(book)
        assertEquals(HttpStatus.CONFLICT, result.statusCode)
    }

    @Test
    fun updateBook() {
        val book = Book(1L)
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(book))
        `when`(bookRepository.save(any(Book::class.java))).thenReturn(book)
        val result = bookService.updateBook(book)
        assertEquals(book, result.body)
    }

    @Test
    fun updateBookNotFound() {
        val book = Book(1L)
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.empty())
        val result = bookService.updateBook(book)
        assertEquals(HttpStatus.CREATED, result.statusCode)
    }

    @Test
    fun deleteBookById() {
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(Book(1L)))
        bookService.deleteBookById(1L)
        assertEquals(HttpStatus.OK, bookService.deleteBookById(1L).statusCode)
    }

    @Test
    fun deleteBookByIdNotFound() {
        doNothing().`when`(bookRepository).deleteById(anyLong())
        bookService.deleteBookById(720L)
        assertEquals(HttpStatus.NOT_FOUND, bookService.deleteBookById(720L).statusCode)
    }
}