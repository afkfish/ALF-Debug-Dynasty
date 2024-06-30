package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations on Sale entities.
 * This interface extends JpaRepository to inherit basic CRUD functionalities.
 * Additionally, it extends SaleRepositoryCustom to include custom query methods.
 */
@Repository
interface SaleRepository: JpaRepository<Sale, Long>, SaleRepositoryCustom