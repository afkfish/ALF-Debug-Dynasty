package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Sale
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Implementation of the SaleRepositoryCustom interface for defining custom query methods related to Sale entities.
 */
@Repository
class SaleRepositoryCustomImpl: SaleRepositoryCustom {

    @Autowired
    // The EntityManager for executing JPA queries.
    private lateinit var entityManager: EntityManager

    /**
     * Retrieves active sales.
     *
     * @return A list of active sales.
     */
    override fun getActiveSales(): List<Sale> {
        val query = entityManager.createQuery("SELECT s FROM Sale s WHERE s.endDate >= CURRENT_DATE", Sale::class.java)
        return query.resultList
    }
}