package org.cars.persistance.entity

import jakarta.persistence.*
import org.cars.api.dto.CarDto

@Entity
@Table(name = "cars")
internal class Car(
    var brand: String,
    var model: String,
    var color: String,
    var year: Int,
    var price: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    fun savedId(): Long = checkNotNull(id) { "Entity has not been saved" }
}

internal fun Car.toDto() =
    CarDto(
        id = savedId(),
        brand = brand,
        model = model,
        color = color,
        year = year,
        price = price
    )