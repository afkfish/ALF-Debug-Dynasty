package hu.aut.bme.springbootalf.repository

import hu.aut.bme.springbootalf.model.Sale

/**
 * Custom repository interface for defining additional query methods related to Sale entities.
 */
interface SaleRepositoryCustom {
    /**
     * Retrieves active sales.
     *
     * @return A list of active sales.
     */
    fun getActiveSales(): List<Sale>
}