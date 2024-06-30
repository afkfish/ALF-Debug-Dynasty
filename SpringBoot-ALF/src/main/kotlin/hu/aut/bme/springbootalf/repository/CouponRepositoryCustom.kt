package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Coupon

/**
 * Custom repository interface for defining additional query methods related to Coupon entities.
 */
interface CouponRepositoryCustom {
    /**
     * Finds coupons by their unique code.
     *
     * @param code The unique code of the coupon to search for.
     * @return A list of coupons matching the specified code.
     */
    fun findByCode(code: String): List<Coupon>
}