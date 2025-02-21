package org.cars.api.dto

data class CarDto(
    val id: Long,
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val price: Int
)
