package hu.aut.bme.springbootalf.dto

import hu.aut.bme.springbootalf.model.Coupon
import java.time.LocalDate

/**
 * Data Transfer Object (DTO) representing a Coupon.
 * This class is used for transferring Coupon data between layers of the application.
 */
class CouponDTO(
    var code: String,                       // The code of the coupon.
    var percentage: Int,                    // The percentage discount offered by the coupon.
    var startDate: LocalDate                // The start date of the coupon validity period.
) {
    // Secondary constructor that initializes a CouponDTO object from a Coupon object.
    constructor(coupon: Coupon) : this("", 0, LocalDate.now()) {
        // Copying data from the Coupon object to the CouponDTO object.
        code = coupon.code
        percentage = coupon.percentage
        startDate = coupon.startDate
    }
}