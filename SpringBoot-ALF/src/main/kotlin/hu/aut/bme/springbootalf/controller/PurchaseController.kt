package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.service.PurchaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * Controller class for managing purchases.
 * Endpoints are secured based on user roles.
 */
@Validated
@RestController
@RequestMapping("/api/purchase")
class PurchaseController {

    @Autowired
    private lateinit var purchaseService: PurchaseService

    /**
     * Initiates a purchase.
     *
     * Endpoint: GET /api/purchase/make
     * Requires: ROLE_USER
     *
     * @param bookId The ID of the book to purchase.
     * @param coupon Optional coupon code for the purchase.
     * @param principal The authenticated user making the purchase.
     * @return ResponseEntity containing the status of the purchase operation.
     */
    @GetMapping("/make")
    @Secured("ROLE_USER")
    fun makePurchase(@RequestParam bookId: Long, @RequestParam coupon: String?, principal: Principal): ResponseEntity<out Any> {
        ALFLogger.logDebug("POST request purchase book where id = $bookId by user ${principal.name}")
        return purchaseService.makePurchase(bookId, principal.name, coupon)
    }

    /**
     * Retrieves all purchases.
     *
     * Endpoint: GET /api/purchase
     * Requires: ROLE_ADMIN
     *
     * @return ResponseEntity containing a list of purchases or an error message.
     */
    @GetMapping("")
    @Secured("ROLE_ADMIN")
    fun getAllPurchases(): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request all purchases by admin")
        return purchaseService.getAllPurchases()
    }

    /**
     * Retrieves a purchase by its ID.
     *
     * Endpoint: GET /api/purchase/{purchaseId}
     * Requires: ROLE_USER
     *
     * @param purchaseId The ID of the purchase to retrieve.
     * @param principal The authenticated user retrieving the purchase.
     * @return ResponseEntity containing the purchase or an error message.
     */
    @GetMapping("/{purchaseId}")
    @Secured("ROLE_USER")
    fun getPurchaseById(@PathVariable purchaseId: Long, principal: Principal): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request purchase where id = $purchaseId by a user")
        return purchaseService.getPurchaseById(purchaseId, principal.name)
    }
}