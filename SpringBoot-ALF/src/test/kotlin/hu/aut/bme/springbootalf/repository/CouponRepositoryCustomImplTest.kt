package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Coupon
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import java.lang.reflect.Field
import java.time.LocalDate

@SpringBootTest
class CouponRepositoryCustomImplTest {

    @Test
    fun findByCode() {
        // 1. Mock the EntityManager object
        val entityManager = mock(EntityManager::class.java)

        // 2. Inject the mocked EntityManager into CouponRepositoryCustomImpl
        val couponRepository = CouponRepositoryCustomImpl()

        // Use reflection to set the entityManager field
        val field: Field = CouponRepositoryCustomImpl::class.java.getDeclaredField("entityManager")
        field.isAccessible = true
        field.set(couponRepository, entityManager)

        // 3. Define a test Coupon object and a test code
        val testCoupon = Coupon(5, LocalDate.now())
        val testCode = "testCode"

        // 4. Set up the EntityManager.createQuery method to return a mock TypedQuery when called with the correct SQL and Coupon class
        val query = mock(TypedQuery::class.java)
        `when`(entityManager.createQuery("SELECT c FROM Coupon c WHERE c.code = :code", Coupon::class.java)).thenReturn(
            query as TypedQuery<Coupon>?
        )

        // 5. Set up the mock TypedQuery.setParameter method to return the same mock TypedQuery when called with the correct parameter name and value
        `when`(query.setParameter("code", testCode)).thenReturn(query)

        // 6. Set up the mock TypedQuery.getResultList method to return a list containing the test Coupon when called
        `when`(query.resultList).thenReturn(listOf(testCoupon))

        // 7. Call the findByCode method of CouponRepositoryCustomImpl with the test code
        val result = couponRepository.findByCode(testCode)

        // 8. Verify that the returned list contains the test Coupon
        assertEquals(1, result.size)
        assertEquals(testCoupon, result[0])
    }
}