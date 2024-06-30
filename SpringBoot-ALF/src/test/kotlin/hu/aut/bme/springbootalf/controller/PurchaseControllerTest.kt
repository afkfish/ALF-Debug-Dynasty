package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.service.PurchaseService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import java.security.Principal

@SpringBootTest
class PurchaseControllerTest {

    @InjectMocks
    private lateinit var purchaseController: PurchaseController

    @Mock
    private lateinit var purchaseService: PurchaseService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun makePurchase() {
        val mockPrincipal = Principal { "alma" }
        `when`(purchaseService.makePurchase(1L, mockPrincipal.name)).thenReturn(ResponseEntity.ok().build())

        val response = purchaseController.makePurchase(1L, null, mockPrincipal)
        assertTrue(response.statusCode.is2xxSuccessful)
    }

    @Test
    fun getAllPurchases() {
        `when`(purchaseService.getAllPurchases()).thenReturn(ResponseEntity.ok().build())

        val response = purchaseController.getAllPurchases()
        assertTrue(response.statusCode.is2xxSuccessful)
    }


    @Test
    fun getPurchaseById() {
        val mockPrincipal = Principal { "testUser" }
        `when`(purchaseService.getPurchaseById(1L, mockPrincipal.name)).thenReturn(ResponseEntity.ok().build())

        val response = purchaseController.getPurchaseById(1L, mockPrincipal)
        assertTrue(response.statusCode.is2xxSuccessful)
    }
}