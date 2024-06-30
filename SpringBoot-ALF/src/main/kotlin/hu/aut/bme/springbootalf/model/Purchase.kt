package hu.aut.bme.springbootalf.model

import jakarta.persistence.*

/**
 * Entity representing a purchase in the application.
 * This class defines the structure of a purchase and its associated properties.
 */
@Entity
@Table(name = "purchases")
class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The unique identifier of the purchase.
    var id: Long = 0

    @ManyToOne
    // The book associated with the purchase.
    var book: Book

    @ManyToOne
    // The user who made the purchase.
    var user: User

    @OneToOne
    @JoinColumn(name = "sale_id")
    // The sale associated with the purchase, if applicable.
    var sale: Sale? = null

    @OneToOne
    @JoinColumn(name = "coupon_id", unique = true)
    // The coupon applied to the purchase, if any.
    var coupon: Coupon? = null

    @Column(name = "final_price")
    // The final price of the purchase.
    var finalPrice: Double = 0.0

    /**
     * Primary constructor to initialize a purchase with a book, user, and final price.
     *
     * @param book The book associated with the purchase.
     * @param user The user who made the purchase.
     * @param finalPrice The final price of the purchase.
     */
    constructor(book: Book, user: User, finalPrice: Double) {
        this.book = book
        this.user = user
        this.finalPrice = finalPrice
    }
}