package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Coupon
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Implementation of the CouponRepositoryCustom interface for defining custom query methods related to Coupon entities.
 */
@Repository
class CouponRepositoryCustomImpl: CouponRepositoryCustom {

    @Autowired
    // The EntityManager for executing JPA queries.
    private lateinit var entityManager: EntityManager

    /**
     * Finds coupons by their unique code.
     *
     * @param code The unique code of the coupon to search for.
     * @return A list of coupons matching the specified code.
     */
    override fun findByCode(code: String): List<Coupon> {
        return entityManager.createQuery("SELECT c FROM Coupon c WHERE c.code = :code", Coupon::class.java)
            .setParameter("code", code)
            .resultList
    }


}