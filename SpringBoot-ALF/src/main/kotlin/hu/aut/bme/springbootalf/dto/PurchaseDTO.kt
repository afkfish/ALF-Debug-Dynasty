package hu.aut.bme.springbootalf.dto

import hu.aut.bme.springbootalf.model.Purchase

/**
 * Data Transfer Object (DTO) representing a Purchase.
 * This class is used for transferring Purchase data between layers of the application.
 */
class PurchaseDTO(
    var book: BookDTO?,                     // The book purchased, represented as a BookDTO object.
    var salePercentage: Int,                // The percentage discount applied to the purchase.
    var finalPrice: Double,                 // The final price of the purchase after discounts.
    var username: String,                   // The username of the user who made the purchase.
    var userBalance: Double                 // The balance of the user after the purchase.
) {
    // Secondary constructor that initializes a PurchaseDTO object from a Purchase object.
    constructor(purchase: Purchase) : this( null, 0, 0.0, "", 0.0) {
        // Copying data from the Purchase object to the PurchaseDTO object.
        book = BookDTO(purchase.book)
        salePercentage = purchase.sale?.percentage ?: 0
        finalPrice = purchase.finalPrice
        username = purchase.user.username
        userBalance = purchase.user.balance
    }
}