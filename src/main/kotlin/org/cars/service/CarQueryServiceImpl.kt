package org.cars.service

import org.cars.api.dto.CarDto
import org.cars.api.service.CarQueryService
import org.cars.persistance.entity.Car
import org.cars.persistance.entity.toDto
import org.cars.persistance.repositoory.CarRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
internal class CarQueryServiceImpl(
    private val carRepository: CarRepository,
) : CarQueryService {

    override fun getCarById(id: Long): CarDto {
        return carRepository.getReferenceById(id).toDto()
    }

    override fun listCars(): List<CarDto> {
        return carRepository.findAll().map(Car::toDto)
    }
}