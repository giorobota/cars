package org.cars.api.service

import org.cars.api.dto.CarDto
import org.cars.api.input.CarInput

interface CarUpdateService {
    fun updateCar(id: Long, carInput: CarInput): CarDto
    fun createCar(carInput: CarInput): CarDto
    fun deleteCar(id: Long): CarDto?
}