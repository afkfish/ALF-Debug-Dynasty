package hu.aut.bme.springbootalf.components

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.dto.BookDTO
import hu.aut.bme.springbootalf.dto.UserDTO
import hu.aut.bme.springbootalf.enums.Genre
import hu.aut.bme.springbootalf.model.Book
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import hu.aut.bme.springbootalf.service.UserService
import hu.aut.bme.springbootalf.service.BookService
import hu.aut.bme.springbootalf.service.PurchaseService
import org.springframework.http.HttpStatus

@Component
class TestingComponent {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var bookService: BookService

    @Autowired
    private lateinit var paymentService: PurchaseService

    private val testResults = mutableListOf<String>()

    fun runTests() {
        // Create a user
        val user= UserDTO()
        user.username="testUser"
        user.balance=200.0
        user.email="tesztelek@gmail.com"
        user.password="testPassword"

        val userCreationResult = userService.createUser(user)
        if (userCreationResult.statusCode != HttpStatus.CREATED) {
            testResults.add("-User creation test failed")
        } else {
            testResults.add("+User creation is working fine")
        }

        // Check user balance
        val balanceCheckResult = userService.addCredit(user.username, 200.0)
        if (balanceCheckResult.statusCode != HttpStatus.OK) {
            testResults.add("-User balance check test failed")
        } else {
            testResults.add("+User balance is working fine")
        }

        // Create a book
        val genre= mutableSetOf<Genre>()
        genre.add(Genre.HORROR)
        val testContent=ByteArray(1000)
        val book=Book("testTitle", "testAuthor", "2000",
            genre,  testContent, 150.0)
        val bookCreationResult = bookService.addBook(
            book
        )
        if (bookCreationResult.statusCode != HttpStatus.CREATED) {
            testResults.add("-Book creation test failed")
        } else {
            testResults.add("+Book creation is working fine")
        }

        // Purchase the book
        bookService.getAllBooks()
        val purchaseResult = paymentService.makePurchase(book.id, user.username)
        if (purchaseResult.statusCode != HttpStatus.OK && purchaseResult.statusCode != HttpStatus.CREATED){
            testResults.add("-Book purchase test failed")
        } else {
            testResults.add("+Purchasing books is working fine")
        }

        // List all books
        val listAllBooksResult = bookService.getAllBooks()
        if (listAllBooksResult.statusCode != HttpStatus.OK) {
            testResults.add("-List all books test failed")
        } else {
            testResults.add("+Listing all books is working fine")
        }

        // Get user's books
        val getUserBooksResult = userService.getUserBooks(user.username)
        if (getUserBooksResult.statusCode != HttpStatus.OK) {
            testResults.add("-Get user's books test failed")
        } else {
            testResults.add("+Getting user's books is working fine")
        }

        // Get book content
        val usersBook= getUserBooksResult.body as Set<*>
        val firstBookId= (usersBook.first() as BookDTO).bookId
        val getBookContentResult = bookService.getBookById(firstBookId)
        if (getBookContentResult.statusCode != HttpStatus.OK) {
            testResults.add("-Get book content test failed")
        } else {
            testResults.add("+Getting book content is working fine")
        }

        // Print all test results
        testResults.forEach { ALFLogger.logInfo(it) }
    }
}