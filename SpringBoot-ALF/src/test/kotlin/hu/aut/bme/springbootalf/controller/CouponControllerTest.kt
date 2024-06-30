package hu.aut.bme.springbootalf.controller

import hu.aut.bme.springbootalf.model.Coupon
import hu.aut.bme.springbootalf.service.CouponService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.util.*

@SpringBootTest
class CouponControllerTest {

    @InjectMocks
    private lateinit var couponController: CouponController

    @Mock
    private lateinit var couponService: CouponService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllCoupons() {
        val mockCoupons = listOf(mock(Coupon::class.java), mock(Coupon::class.java))
        `when`(couponService.getAllCoupons()).thenReturn(ResponseEntity(mockCoupons, HttpStatus.OK))
        val response = couponController.getAllCoupons()
        assertTrue(response.body is List<*>)
        assertEquals(2, (response.body as List<*>).size)
    }

    @Test
    fun getCouponById() {
        val couponId: Long = 1234
        val mockCoupon: Coupon = mock(Coupon::class.java)
        `when`(couponService.getCouponById(couponId)).thenReturn(ResponseEntity(Optional.of(mockCoupon),HttpStatus.OK))
        val response = couponController.getCouponById(couponId)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Optional.of(mockCoupon), response.body)
    }

    @Test
    fun addCoupon() {
        val coupon = Coupon(10, LocalDate.now())
        `when`(couponService.addCoupon(coupon)).thenReturn(ResponseEntity(coupon, HttpStatus.CREATED))
        val response = couponController.addCoupon(coupon)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun getCouponByCode() {
        val mockCoupon: Coupon = mock(Coupon::class.java)
        `when`(couponService.getCouponByCode("test")).thenReturn(ResponseEntity(mockCoupon, HttpStatus.OK))
        val response = couponController.getCouponByCode("test")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(mockCoupon, response.body)
    }

    @Test
    fun validateCoupon() {
        val mockCoupon: Coupon = mock(Coupon::class.java)
        `when`(couponService.validateCoupon("test")).thenReturn(ResponseEntity(mockCoupon, HttpStatus.OK))
        val response = couponController.validateCoupon("test")
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(mockCoupon, response.body)
    }
}