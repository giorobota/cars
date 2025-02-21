package org.cars.service

import org.cars.api.dto.CarDto
import org.cars.api.input.CarInput
import org.cars.api.service.CarUpdateService
import org.cars.persistance.entity.Car
import org.cars.persistance.entity.toDto
import org.cars.persistance.repositoory.CarRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
internal class CarUpdateServiceImpl(
    private val carRepository: CarRepository,
) : CarUpdateService {

    @Transactional
    override fun updateCar(id: Long, carInput: CarInput): CarDto {
        val car = carRepository.getReferenceById(id)
        car.year = carInput.year
        car.brand = carInput.brand
        car.color = carInput.color
        car.price = carInput.price
        car.model = carInput.model
        return car.toDto()
    }

    @Transactional
    override fun createCar(carInput: CarInput): CarDto {
        val car = carInput.toCar()
        return carRepository.save(car).toDto()
    }

    override fun deleteCar(id: Long): CarDto? {
        val car = carRepository.findById(id)
        if (car.isPresent) {
            carRepository.deleteById(id)
        }
        return car.getOrNull()?.toDto()
    }

    private fun CarInput.toCar() =
        Car(
            year = year,
            brand = brand,
            color = color,
            price = price,
            model = model,
        )
}