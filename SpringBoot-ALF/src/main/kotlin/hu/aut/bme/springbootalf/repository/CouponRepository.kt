package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations on Coupon entities.
 * This interface extends JpaRepository to inherit basic CRUD functionalities.
 * Additionally, it extends CouponRepositoryCustom to include custom query methods.
 */
@Repository
interface CouponRepository: JpaRepository<Coupon, Long>, CouponRepositoryCustom