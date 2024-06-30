package hu.aut.bme.springbootalf.model

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDate

@Entity
@Table(name = "sale")
class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var startDate: LocalDate

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var endDate: LocalDate

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 100)
    var percentage: Int = 0

    constructor(startDate: LocalDate, endDate: LocalDate, percentage: Int) {
        this.startDate = startDate
        this.endDate = endDate
        this.percentage = percentage
    }
}