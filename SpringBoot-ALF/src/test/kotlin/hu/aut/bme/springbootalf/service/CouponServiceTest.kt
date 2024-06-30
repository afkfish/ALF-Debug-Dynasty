package hu.aut.bme.springbootalf.service

import hu.aut.bme.springbootalf.dto.CouponDTO
import hu.aut.bme.springbootalf.model.Coupon
import hu.aut.bme.springbootalf.repository.CouponRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.util.*

@SpringBootTest
class CouponServiceTest {

    @InjectMocks
    private lateinit var couponService: CouponService

    @Mock
    private lateinit var couponRepository: CouponRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllCoupons() {
        val coupons = listOf(Coupon(10, LocalDate.now()),
                Coupon(10, LocalDate.now()),
                Coupon(10, LocalDate.now()))
        `when`(couponRepository.findAll()).thenReturn(coupons)
        val result = couponService.getAllCoupons()
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(coupons, result.body)
    }

    @Test
    fun getCouponById() {
        val coupon =Coupon(10, LocalDate.now())
        `when`(couponRepository.findById(1L)).thenReturn(Optional.of(coupon))
        val result = couponService.getCouponById(1L)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(coupon, result.body)
    }

    @Test
    fun getCouponByIdNotFound(){
        `when`(couponRepository.findById(2L)).thenReturn(Optional.empty())
        val result = couponService.getCouponById(2L)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun getCouponByCode() {
        val coupon =Coupon(10, LocalDate.now())
        `when`(couponRepository.findByCode("test")).thenReturn(listOf(coupon))
        val result = couponService.getCouponByCode("test")
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(coupon.code, (result.body as CouponDTO).code)
    }

    @Test
    fun getCouponByCodeNotFound(){
        `when`(couponRepository.findByCode("test")).thenReturn(listOf())
        val result = couponService.getCouponByCode("test")
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun addCoupon() {
        val coupon = Coupon(10, LocalDate.now())
        `when`(couponRepository.save(coupon)).thenReturn(coupon)
        val result = couponService.addCoupon(coupon)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(coupon, result.body)
    }

    @Test
    fun validateCoupon() {
        val coupon =Coupon(10, LocalDate.now()).apply { startDate = LocalDate.now().minusDays(1) }
        `when`(couponRepository.findByCode("test")).thenReturn(listOf(coupon))
        val result = couponService.validateCoupon("test")
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(coupon.code, (result.body as CouponDTO).code)
    }

    @Test
    fun validateCouponFuture(){
        val coupon =Coupon(10, LocalDate.now()).apply { startDate = LocalDate.now().plusDays(1) }
        `when`(couponRepository.findByCode("test")).thenReturn(listOf(coupon))
        val result = couponService.validateCoupon("test")
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }
    @Test
    fun validateCouponUsed(){
        `when`(couponRepository.findByCode("test")).thenReturn(listOf())
        val result = couponService.validateCoupon("test")
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun deactivateCoupon() {
        val coupon =Coupon(10, LocalDate.now())
        `when`(couponRepository.save(coupon)).thenReturn(coupon)
        couponService.deactivateCoupon(coupon)
        assertEquals(true, coupon.used)
    }
}