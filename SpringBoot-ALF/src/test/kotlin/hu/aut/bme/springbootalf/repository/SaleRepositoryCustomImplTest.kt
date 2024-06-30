package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Sale
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import java.lang.reflect.Field
import java.time.LocalDate

@SpringBootTest
class SaleRepositoryCustomImplTest {

    @Test
    fun getActiveSales() {
        // 1. Mock the EntityManager object
        val entityManager = mock(EntityManager::class.java)

        // 2. Inject the mocked EntityManager into SaleRepositoryCustomImpl
        val saleRepository = SaleRepositoryCustomImpl()

        // Use reflection to set the entityManager field
        val field: Field = SaleRepositoryCustomImpl::class.java.getDeclaredField("entityManager")
        field.isAccessible = true
        field.set(saleRepository, entityManager)

        // 3. Define a test Sale object
        val testSale = Sale(LocalDate.now(), LocalDate.now().plusDays(5), 5)

        // 4. Set up the EntityManager.createQuery method to return a mock TypedQuery when called with the correct SQL and Sale class
        val query = mock(TypedQuery::class.java)
        `when`(entityManager.createQuery("SELECT s FROM Sale s WHERE s.endDate >= CURRENT_DATE", Sale::class.java)).thenReturn(
            query as TypedQuery<Sale>?
        )

        // 5. Set up the mock TypedQuery.getResultList method to return a list containing the test Sale when called
        `when`(query.resultList).thenReturn(listOf(testSale))

        // 6. Call the getActiveSales method of SaleRepositoryCustomImpl
        val result = saleRepository.getActiveSales()

        // 7. Verify that the returned list contains the test Sale
        assertEquals(1, result.size)
        assertEquals(testSale, result[0])
    }
}