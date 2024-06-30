package hu.aut.bme.springbootalf.model

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDate

/**
 * Entity representing a coupon in the application.
 * This class defines the structure of a coupon and its associated properties.
 */
@Entity
@Table(name = "coupon", indexes = [Index(name = "code_idx", columnList = "code", unique = true)])
class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The unique identifier of the coupon.
    var id: Long = 0

    @Column(unique = true, nullable = false)
    // The unique code of the coupon.
    var code: String = generateCode(8)

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 100)
    // The discount percentage offered by the coupon.
    var percentage: Int = 0

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    // The start date of the coupon validity period.
    var startDate: LocalDate

    @Column(nullable = false)
    // Indicates whether the coupon has been used.
    var used: Boolean = false

    /**
     * Primary constructor to initialize a coupon with a percentage and start date.
     *
     * @param percentage The discount percentage offered by the coupon.
     * @param startDate The start date of the coupon validity period.
     */
    constructor(percentage: Int, startDate: LocalDate) {
        this.percentage = percentage
        this.startDate = startDate
    }

    /**
     * Generates a random code for the coupon.
     *
     * @param length The length of the generated code.
     * @return The generated code for the coupon.
     */
    private fun generateCode(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}