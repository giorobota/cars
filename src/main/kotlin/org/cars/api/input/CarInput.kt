package org.cars.api.input

data class CarInput(
    val brand: String,
    val model: String,
    val color: String,
    val year: Int,
    val price: Int
)
