package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.ALFLogger
import hu.aut.bme.springbootalf.model.Sale
import hu.aut.bme.springbootalf.service.SaleService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Controller class for managing sales.
 */
@Validated
@RestController
@RequestMapping("/api/sale")
class SaleController {

    @Autowired
    private lateinit var saleService: SaleService

    /**
     * Retrieves all active sales.
     *
     * Endpoint: GET /api/sale
     * Requires: ROLE_USER
     *
     * @return ResponseEntity containing a list of active sales or an error message.
     */
    @GetMapping("")
    @Secured("ROLE_USER")
    fun getAllSales(): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request all sales by a user")
        return saleService.getActiveSales()
    }

    /**
     * Retrieves a sale by its ID.
     *
     * Endpoint: GET /api/sale/{saleId}
     * Requires: ROLE_USER
     *
     * @param saleId The ID of the sale to retrieve.
     * @return ResponseEntity containing the sale or an error message.
     */
    @GetMapping("/{saleId}")
    @Secured("ROLE_USER")
    fun getSaleById(@PathVariable saleId: Long): ResponseEntity<out Any> {
        ALFLogger.logDebug("GET request sale where id = $saleId by a user")
        return saleService.getSaleById(saleId)
    }

    /**
     * Adds a new sale.
     *
     * Endpoint: POST /api/sale
     * Requires: ROLE_ADMIN
     *
     * @param sale The sale information to add.
     * @return ResponseEntity containing the status of the addition operation.
     */
    @PostMapping("")
    @Secured("ROLE_ADMIN")
    fun addSale(@RequestBody @Valid sale: Sale): ResponseEntity<out Any> {
        ALFLogger.logDebug("POST request sale = $sale by admin")
        return saleService.addSale(sale)
    }
}