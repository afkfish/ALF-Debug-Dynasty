package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.dto.CouponDTO
import hu.aut.bme.springbootalf.model.Coupon
import hu.aut.bme.springbootalf.repository.CouponRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * Service class for managing Coupon entities.
 * This class provides methods for CRUD operations on Coupon entities,
 * as well as for validating and deactivating coupons.
 */
@Service
class CouponService {
    
    @Autowired
    private lateinit var couponRepository: CouponRepository

    /**
     * Retrieves all coupons.
     *
     * @return A ResponseEntity containing the list of all coupons or an appropriate error response.
     */
    fun getAllCoupons(): ResponseEntity<out Any> {
        return ResponseEntity<List<Coupon>>(couponRepository.findAll(), HttpStatus.OK)
    }

    /**
     * Retrieves a coupon by its ID.
     *
     * @param id The ID of the coupon to retrieve.
     * @return A ResponseEntity containing the coupon with the specified ID or an appropriate error response.
     */
    fun getCouponById(id: Long): ResponseEntity<out Any> {
        val coupon = couponRepository.findById(id)
        if(coupon.isEmpty) {
            return ResponseEntity<String>("Coupon NOT found with ID: $id", HttpStatus.NOT_FOUND)
        }
        return ResponseEntity<Coupon>(coupon.get(), HttpStatus.OK)
    }

    /**
     * Retrieves a coupon by its code.
     *
     * @param code The code of the coupon to retrieve.
     * @return A ResponseEntity containing the coupon with the specified code or an appropriate error response.
     */
    fun getCouponByCode(code: String): ResponseEntity<out Any> {
        val coupon = couponRepository.findByCode(code).firstOrNull()
            ?: return ResponseEntity<String>("Coupon NOT found with code: $code", HttpStatus.NOT_FOUND)
        return ResponseEntity<CouponDTO>(CouponDTO(coupon), HttpStatus.OK)
    }

    /**
     * Adds a new coupon.
     *
     * @param coupon The coupon to add.
     * @return A ResponseEntity containing the added coupon or an appropriate error response.
     */
    fun addCoupon(coupon: Coupon): ResponseEntity<out Any> {
        return ResponseEntity<Coupon>(couponRepository.save(coupon), HttpStatus.CREATED)
    }

    /**
     * Validates a coupon by its code.
     *
     * @param code The code of the coupon to validate.
     * @return A ResponseEntity indicating the validation status or an appropriate error response.
     */
    fun validateCoupon(code: String): ResponseEntity<out Any> {
        val coupon = couponRepository.findByCode(code).firstOrNull()
            ?: return ResponseEntity<String>("Coupon NOT found with code: $code", HttpStatus.NOT_FOUND)

        if(coupon.used or coupon.startDate.isAfter(LocalDate.now())) {
            return ResponseEntity<String>("Coupon with code: $code is invalid", HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity<CouponDTO>(CouponDTO(coupon), HttpStatus.OK)
    }

    /**
     * Deactivates a coupon.
     *
     * @param coupon The coupon to deactivate.
     */
    fun deactivateCoupon(coupon: Coupon) {
        coupon.used = true
        couponRepository.save(coupon)
    }
}