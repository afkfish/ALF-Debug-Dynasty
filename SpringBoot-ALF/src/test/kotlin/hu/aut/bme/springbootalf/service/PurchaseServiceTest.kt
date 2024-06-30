package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.dto.PurchaseDTO
import hu.aut.bme.springbootalf.enums.Genre
import hu.aut.bme.springbootalf.enums.UserRole
import hu.aut.bme.springbootalf.model.*
import hu.aut.bme.springbootalf.repository.BookRepository
import hu.aut.bme.springbootalf.repository.PurchaseRepository
import hu.aut.bme.springbootalf.repository.SaleRepository
import hu.aut.bme.springbootalf.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.util.*

@SpringBootTest
class PurchaseServiceTest {

    @InjectMocks
    private lateinit var purchaseService: PurchaseService

    @Mock
    private lateinit var purchaseRepository: PurchaseRepository

    @Mock
    private lateinit var bookRepository: BookRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var saleRepository: SaleRepository

    @Mock
    private lateinit var couponService: CouponService

    private lateinit var book: Book
    private lateinit var purchase: Purchase
    private lateinit var user: User
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = User("asd", "asd@ads.com", "asd", listOf(UserRole.ROLE_USER))
        user.balance = 100.0
        book  = Book("asd", "asd", "2020", setOf(Genre.SCFIFY), ByteArray(0), 10.0)
        purchase = Purchase(book, user, 10.0)
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(book))
        `when`(userRepository.findById(anyString())).thenReturn(Optional.of(user))
        `when`(purchaseRepository.save(any(Purchase::class.java))).thenReturn(purchase)
    }



    @Test
    fun getAllPurchases() {
        val purchases = listOf(Purchase(Book(1L), user, 10.0), Purchase(Book(2L), user, 10.0), Purchase(Book(1L), user, 10.0))
        `when`(purchaseRepository.findAll()).thenReturn(purchases)

        val result = purchaseService.getAllPurchases()
        assertEquals(3, (result.body as List<*>).size)
    }

    @Test
    fun getPurchaseById() {
        val purchase = Purchase(Book(1L), user, 10.0)
        `when`(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase))

        val result = purchaseService.getPurchaseById(1L, "asd")
        assertEquals(purchase, result.body)
    }
    @Test
    fun getPurchaseByIdNotFound() {
        `when`(purchaseRepository.findById(anyLong())).thenReturn(Optional.empty())
        val result = purchaseService.getPurchaseById(2L, "MRNegativ")
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun getPurchaseByIdUnauthorized() {
        val purchase = Purchase(Book(1L), user, 10.0)
        `when`(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase))
        val result = purchaseService.getPurchaseById(1L, "MRNegativ")
        assertEquals(HttpStatus.FORBIDDEN, result.statusCode)
    }

    @Test
    fun makePurchase_WithValidBookAndUser_ReturnsPurchase() {
        val result = purchaseService.makePurchase(1L, "asd")
        assertEquals(purchase.book.title, (result.body as PurchaseDTO).book?.title)
    }

    @Test
    fun makePurchase_WithInsufficientBalance_ReturnsForbidden() {
        user.balance = 0.0
        val result = purchaseService.makePurchase(1L, "asd")
        assertEquals(HttpStatus.FORBIDDEN, result.statusCode)
    }

    @Test
    fun makePurchase_WithExistingBook_ReturnsConflict() {
        user.books += book
        val result = purchaseService.makePurchase(1L, "asd")
        assertEquals(HttpStatus.CONFLICT, result.statusCode)
    }

    @Test
    fun makePurchase_WithActiveSale_ReturnsDiscountedPrice() {
        purchase.finalPrice=9.0
        `when`(purchaseRepository.save(any(Purchase::class.java))).thenReturn(purchase)
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleRepository.getActiveSales()).thenReturn(listOf(sale))
        val result = purchaseService.makePurchase(1L, "asd")
        assertEquals(9.0, (result.body as PurchaseDTO).finalPrice)
    }

    @Test
    fun makePurchase_WithValidCoupon_ReturnsDiscountedPrice() {
        purchase.finalPrice=9.0
        `when`(purchaseRepository.save(any(Purchase::class.java))).thenReturn(purchase)
        `when`(couponService.validateCoupon(anyString())).thenReturn(ResponseEntity.ok(Coupon(10, LocalDate.now().plusDays(10))))
        val result = purchaseService.makePurchase(1L, "asd", "asd")
        assertEquals(9.0, (result.body as PurchaseDTO).finalPrice)
    }
}