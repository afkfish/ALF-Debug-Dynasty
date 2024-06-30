package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.model.Sale
import hu.aut.bme.springbootalf.service.SaleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import java.time.LocalDate

@SpringBootTest
class SaleControllerTest {

    @InjectMocks
    private lateinit var saleController: SaleController

    @Mock
    private lateinit var saleService: SaleService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllSales() {
        val sales = listOf(Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10), Sale(LocalDate.now(), LocalDate.now().plusDays(20), 20), Sale(LocalDate.now(),
            LocalDate.now().plusDays(30), 30))
        `when`(saleService.getActiveSales()).thenReturn(ResponseEntity.ok(sales))
        val response = saleController.getAllSales()
        assertTrue(response.statusCode.is2xxSuccessful)
        assertEquals(3, (response.body as List<*>).size)
    }
    @Test
    fun getSaleById() {
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleService.getSaleById(anyLong())).thenReturn(ResponseEntity.ok(sale))
        val response = saleController.getSaleById(1L)
        assertTrue(response.statusCode.is2xxSuccessful)
        assertEquals(sale, response.body)
    }

    @Test
    fun addSale() {
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleService.addSale(sale)).thenReturn(ResponseEntity.ok(sale))
        val response = saleController.addSale(sale)
        assertTrue(response.statusCode.is2xxSuccessful)
        assertEquals(sale, response.body)
    }
}