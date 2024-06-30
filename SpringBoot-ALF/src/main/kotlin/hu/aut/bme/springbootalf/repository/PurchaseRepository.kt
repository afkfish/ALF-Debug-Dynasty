package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for performing CRUD operations on Purchase entities.
 * This interface extends JpaRepository to inherit basic CRUD functionalities.
 */
@Repository
interface PurchaseRepository: JpaRepository<Purchase, Long>