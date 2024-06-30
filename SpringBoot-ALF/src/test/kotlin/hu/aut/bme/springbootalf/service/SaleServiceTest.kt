package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.model.Sale
import hu.aut.bme.springbootalf.repository.SaleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
class SaleServiceTest {

    @InjectMocks
    private lateinit var saleService: SaleService

    @Mock
    private lateinit var saleRepository: SaleRepository

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllSales() {
        val sales = listOf(Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10), Sale(LocalDate.now(), LocalDate.now().plusDays(20), 20), Sale(LocalDate.now(),
            LocalDate.now().plusDays(30), 30))
        `when`(saleRepository.findAll()).thenReturn(sales)
        val result = saleService.getAllSales()
        assertEquals(3, (result.body as List<*>).size)
    }

    @Test
    fun getSaleById() {
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleRepository.findById(anyLong())).thenReturn(Optional.of(sale))
        var result = saleService.getSaleById(1L)
        assertEquals(sale, result.body)
    }
    @Test
    fun getSaleByIdNotFound() {
        `when`(saleRepository.findById(anyLong())).thenReturn(Optional.empty())
        val result = saleService.getSaleById(2L)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun addSale() {
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleRepository.save(any(Sale::class.java))).thenReturn(sale)
        var result = saleService.addSale(sale)
        assertEquals(sale, result.body)
    }
    @Test
    fun addSaleAlreadyExists(){
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10)
        `when`(saleRepository.findById(anyLong())).thenReturn(Optional.of(sale))
        val result = saleService.addSale(sale)
        assertEquals(HttpStatus.CONFLICT, result.statusCode)
    }
    @Test
    fun addSaleEndDateExpired(){
        val sale = Sale(LocalDate.now(), LocalDate.now().plusDays(-1), 10)
        `when`(saleRepository.save(any(Sale::class.java))).thenReturn(sale)
        val result = saleService.addSale(sale)
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun getActiveSales() {
        val sales = listOf(Sale(LocalDate.now(), LocalDate.now().plusDays(10), 10), Sale(LocalDate.now(), LocalDate.now().plusDays(20), 20), Sale(LocalDate.now(),
            LocalDate.now().plusDays(30), 30))
        `when`(saleRepository.getActiveSales()).thenReturn(sales)
        val result = saleService.getActiveSales()
        assertEquals(3, (result.body as List<*>).size)
    }
}