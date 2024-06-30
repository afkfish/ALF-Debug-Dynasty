package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.dto.PurchaseDTO
import hu.aut.bme.springbootalf.model.Coupon
import hu.aut.bme.springbootalf.model.Purchase
import hu.aut.bme.springbootalf.repository.BookRepository
import hu.aut.bme.springbootalf.repository.PurchaseRepository
import hu.aut.bme.springbootalf.repository.SaleRepository
import hu.aut.bme.springbootalf.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Service class for managing purchases.
 * This class provides methods for handling purchase-related operations such as retrieving purchases,
 * making purchases, and adding purchases.
 */
@Service
class PurchaseService {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var saleRepository: SaleRepository

    @Autowired
    private lateinit var couponService: CouponService

    /**
     * Retrieves all purchases.
     *
     * @return A ResponseEntity containing the list of all purchases or an appropriate error response.
     */
    fun getAllPurchases(): ResponseEntity<out Any> {
        ALFLogger.logInfo("All purchases were requested")
        return ResponseEntity<List<Purchase>>(purchaseRepository.findAll().toMutableList(), HttpStatus.OK)
    }

    /**
     * Retrieves a purchase by its ID.
     *
     * @param id The ID of the purchase to retrieve.
     * @param principal The username of the principal user.
     * @return A ResponseEntity containing the purchase with the specified ID or an appropriate error response.
     */
    fun getPurchaseById(id: Long, principal: String): ResponseEntity<out Any> {
        val purchase = purchaseRepository.findById(id)
        if(purchase.isEmpty) {
            ALFLogger.logError("Purchase NOT found with ID: $id")
            return ResponseEntity<String>("Purchase NOT found with ID: $id", HttpStatus.NOT_FOUND)
        }
        else {
            ALFLogger.logInfo("Purchase WAS found with ID $id")
            if (purchase.get().user.username != principal) {
                ALFLogger.logError("User $principal is not allowed to see purchase with ID: $id")
                return ResponseEntity<String>("User $principal is unauthorized to see purchase with ID: $id", HttpStatus.FORBIDDEN)
            }
            return ResponseEntity<Purchase>(purchase.get(), HttpStatus.OK)
        }
    }

    /**
     * Adds a new purchase.
     *
     * @param purchase The purchase to add.
     * @return A ResponseEntity containing the added purchase or an appropriate error response.
     */
    private fun addPurchase(purchase: Purchase): ResponseEntity<out Any> {
        val user = purchase.user
        val book = purchase.book

        user.balance -= purchase.finalPrice
        user.books += book
        userRepository.save(user)

        ALFLogger.logInfo("Purchase saved by ID: ${purchase.id}")
        return ResponseEntity<PurchaseDTO>(PurchaseDTO(purchaseRepository.save(purchase)), HttpStatus.CREATED)
    }

    /**
     * Makes a purchase for the specified book and user.
     *
     * @param bookId The ID of the book to purchase.
     * @param principal The username of the principal user.
     * @param couponCode The coupon code to apply to the purchase, if any.
     * @return A ResponseEntity indicating success or failure of the purchase operation.
     */
    fun makePurchase(bookId: Long, principal: String, couponCode: String? = null): ResponseEntity<out Any> {
        val user = userRepository.findById(principal).get()
        val book = bookRepository.findById(bookId).get()

        if(user.books.contains(book)) {
            ALFLogger.logWarning("User ${user.username} already has the book with ID: ${book.id}")
            return ResponseEntity<String>("User ${user.username} already has the book with ID: ${book.id}", HttpStatus.CONFLICT)
        }

        var finalPrice = book.price

        val sale = saleRepository.getActiveSales().maxByOrNull { it.percentage }
        if (sale != null) {
            ALFLogger.logInfo("Active sale found with ID: ${sale.id} and percentage: ${sale.percentage}")
            finalPrice *= (1.0 - (sale.percentage.toDouble() / 100.0))
        }

        if (couponCode != null) {
            val coupon = couponService.validateCoupon(couponCode).body as Coupon?

            if (coupon != null) {
                coupon.used = true
                couponService.deactivateCoupon(coupon)

                ALFLogger.logInfo("Coupon found with ID: ${coupon.id} and percentage: ${coupon.percentage}")
                finalPrice *= (1.0 - (coupon.percentage.toDouble() / 100.0))
            }
        }

        ALFLogger.logInfo("Purchase final price: $finalPrice")

        if (user.balance < finalPrice) {
            ALFLogger.logError("Insufficient balance on the $user's account")
            return ResponseEntity<String>("Unsuccessful transaction, insufficient balance", HttpStatus.FORBIDDEN)
        }

        val purchase = Purchase(book = book, user = user, finalPrice = finalPrice)

        ALFLogger.logInfo("Purchase started by ID: ${purchase.id}")
        return addPurchase(purchase)
    }
}