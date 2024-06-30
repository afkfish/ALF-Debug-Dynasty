package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.model.Coupon
import hu.aut.bme.springbootalf.service.CouponService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Controller class for managing coupon-related HTTP requests.
 * Endpoints are secured based on user roles.
 */
@Validated
@RestController
@RequestMapping("/api/coupon")
class CouponController {

    @Autowired
    private lateinit var couponService: CouponService

    /**
     * Retrieves all coupons.
     *
     * Endpoint: GET /api/coupon
     * Requires: ROLE_ADMIN
     *
     * @return ResponseEntity containing a list of coupons or an error message.
     */
    @GetMapping("")
    @Secured("ROLE_ADMIN")
    fun getAllCoupons(): ResponseEntity<out Any> {
        return couponService.getAllCoupons()
    }

    /**
     * Retrieves a coupon by its ID.
     *
     * Endpoint: GET /api/coupon/{couponId}
     * Requires: ROLE_ADMIN
     *
     * @param couponId The ID of the coupon to retrieve.
     * @return ResponseEntity containing the coupon or an error message.
     */
    @GetMapping("/{couponId}")
    @Secured("ROLE_ADMIN")
    fun getCouponById(@PathVariable couponId: Long): ResponseEntity<out Any> {
        return couponService.getCouponById(couponId)
    }

    /**
     * Adds a new coupon.
     *
     * Endpoint: GET /api/coupon/add?coupon={coupon}
     * Requires: ROLE_ADMIN
     *
     * @param coupon The coupon to add.
     * @return ResponseEntity containing the added coupon or an error message.
     */
    @GetMapping("/add")
    @Secured("ROLE_ADMIN")
    fun addCoupon(@RequestParam coupon: Coupon): ResponseEntity<out Any> {
        //val coupon = Coupon(percentage, startDate)
        return couponService.addCoupon(coupon)
    }

    /**
     * Retrieves a coupon by its code.
     *
     * Endpoint: GET /api/coupon/code/{couponCode}
     * Requires: ROLE_USER
     *
     * @param couponCode The code of the coupon to retrieve.
     * @return ResponseEntity containing the coupon or an error message.
     */
    @GetMapping("/code/{couponCode}")
    @Secured("ROLE_USER")
    fun getCouponByCode(@PathVariable couponCode: String): ResponseEntity<out Any> {
        return couponService.getCouponByCode(couponCode)
    }

    /**
     * Validates a coupon by its code.
     *
     * Endpoint: GET /api/coupon/validate/{couponCode}
     * Requires: ROLE_USER
     *
     * @param couponCode The code of the coupon to validate.
     * @return ResponseEntity indicating whether the coupon is valid or not.
     */
    @GetMapping("/validate/{couponCode}")
    @Secured("ROLE_USER")
    fun validateCoupon(@PathVariable couponCode: String): ResponseEntity<out Any> {
        return couponService.validateCoupon(couponCode)
    }
}