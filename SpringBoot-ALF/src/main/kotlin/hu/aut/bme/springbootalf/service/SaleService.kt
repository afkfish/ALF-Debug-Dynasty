package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.model.Sale
import hu.aut.bme.springbootalf.repository.SaleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * Service class for managing sales.
 * This class provides methods for handling sale-related operations such as retrieving sales,
 * adding sales, and getting active sales.
 */
@Service
class SaleService {
    @Autowired
    private lateinit var saleRepository: SaleRepository

    /**
     * Retrieves a sale by its ID.
     *
     * @param id The ID of the sale to retrieve.
     * @return A ResponseEntity containing the sale with the specified ID or an appropriate error response.
     */
    fun getSaleById(id: Long): ResponseEntity<out Any> {
        val sale = saleRepository.findById(id)
        if(sale.isPresent) {
            ALFLogger.logInfo(("Sale WAS found with ID: $id"))
            return ResponseEntity<Sale>(sale.get(), HttpStatus.OK)
        }

        ALFLogger.logError("Sale NOT found with ID: $id")
        return ResponseEntity<String>("Sale not found with ID: $id", HttpStatus.NOT_FOUND)
    }

    /**
     * Retrieves all sales.
     *
     * @return A ResponseEntity containing the list of all sales or an appropriate error response.
     */
    fun getAllSales(): ResponseEntity<out Any> {
        return ResponseEntity<List<Sale>>(saleRepository.findAll().toMutableList(), HttpStatus.OK)
    }

    /**
     * Retrieves all active sales.
     *
     * @return A ResponseEntity containing the list of all active sales or an appropriate error response.
     */
    fun getActiveSales(): ResponseEntity<out Any> {
        val activeSales = saleRepository.getActiveSales()
        return ResponseEntity<List<Sale>>(activeSales, HttpStatus.OK)
    }

    /**
     * Adds a new sale.
     *
     * @param sale The sale to add.
     * @return A ResponseEntity indicating success or failure of the sale addition operation.
     *         If the sale already exists or the end date has expired, returns an appropriate error response.
     */
    fun addSale(sale: Sale): ResponseEntity<out Any> {
        val alreadyExisting = saleRepository.findById(sale.id)
        if(alreadyExisting.isPresent) {
            ALFLogger.logWarning("Sale already exists with ID: $sale.id")
            return ResponseEntity<String>("Sale already exists with ID: $sale.id", HttpStatus.CONFLICT)
        }

        if(sale.endDate.isBefore(LocalDate.now())) {
            ALFLogger.logWarning("The end date has expired")
            return ResponseEntity<String>("The end date has expired", HttpStatus.BAD_REQUEST)
        }

        ALFLogger.logInfo("Sale added witgh id: $sale.id")
        return ResponseEntity<Sale>(saleRepository.save(sale), HttpStatus.OK)
    }
}