package org.cars.api.service

import org.cars.api.dto.CarDto

interface CarQueryService {
    fun getCarById(id: Long): CarDto
    fun listCars(): List<CarDto>
}