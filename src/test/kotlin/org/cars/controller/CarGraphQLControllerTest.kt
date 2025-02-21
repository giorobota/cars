package org.cars.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.cars.api.dto.CarDto
import org.cars.api.input.CarInput
import org.cars.api.service.CarQueryService
import org.cars.api.service.CarUpdateService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class CarGraphQLControllerTest {

    private lateinit var carQueryService: CarQueryService
    private lateinit var carUpdateService: CarUpdateService
    private lateinit var carGraphQLController: CarGraphQLController

    @BeforeEach
    fun setUp() {
        carQueryService = mockk()
        carUpdateService = mockk()
        carGraphQLController = CarGraphQLController(carQueryService, carUpdateService)
    }

    @Test
    fun `getAllCars should return a list of cars`() {
        val cars = listOf(
            CarDto(1L, "Toyota", "Corolla", "Red", 2020, 20000),
            CarDto(2L, "Honda", "Civic", "Blue", 2022, 25000)
        )

        every { carQueryService.listCars() } returns cars

        val result = carGraphQLController.getAllCars()

        assertEquals(2, result.size)
        assertEquals("Toyota", result[0].brand)
        assertEquals("Honda", result[1].brand)

        verify(exactly = 1) { carQueryService.listCars() }
    }

    @Test
    fun `getCarById should return a car when found`() {
        val car = CarDto(1L, "Toyota", "Corolla", "Red", 2020, 20000)

        every { carQueryService.getCarById(1L) } returns car

        val result = carGraphQLController.getCarById(1L)

        assertNotNull(result)
        assertEquals("Toyota", result.brand)
        assertEquals("Red", result.color)
        assertEquals(20000, result.price)

        verify(exactly = 1) { carQueryService.getCarById(1L) }
    }

    @Test
    fun `addCar should create and return a new car`() {
        val carInput = CarInput("Ford", "Focus", "Black", 2023, 22000)
        val savedCar = CarDto(3L, "Ford", "Focus", "Black", 2023, 22000)

        every { carUpdateService.createCar(carInput) } returns savedCar

        val result = carGraphQLController.addCar(carInput)

        assertNotNull(result)
        assertEquals("Ford", result.brand)
        assertEquals("Black", result.color)
        assertEquals(22000, result.price)

        verify(exactly = 1) { carUpdateService.createCar(carInput) }
    }

    @Test
    fun `updateCar should modify and return the updated car`() {
        val carInput = CarInput("Tesla", "Model 3", "White", 2023, 45000)
        val updatedCar = CarDto(1L, "Tesla", "Model 3", "White", 2023, 45000)

        every { carUpdateService.updateCar(1L, carInput) } returns updatedCar

        val result = carGraphQLController.updateCar(1L, carInput)

        assertNotNull(result)
        assertEquals("Tesla", result.brand)
        assertEquals("White", result.color)
        assertEquals(45000, result.price)

        verify(exactly = 1) { carUpdateService.updateCar(1L, carInput) }
    }

    @Test
    fun `deleteCar should return deleted car if found`() {
        val deletedCar = CarDto(1L, "Toyota", "Camry", "Silver", 2019, 18000)

        every { carUpdateService.deleteCar(1L) } returns deletedCar

        val result = carGraphQLController.deleteCar(1L)

        assertNotNull(result)
        assertEquals("Toyota", result?.brand)
        assertEquals("Silver", result?.color)
        assertEquals(18000, result?.price)

        verify(exactly = 1) { carUpdateService.deleteCar(1L) }
    }

    @Test
    fun `deleteCar should return null if car not found`() {
        every { carUpdateService.deleteCar(99L) } returns null

        val result = carGraphQLController.deleteCar(99L)

        assertNull(result)

        verify(exactly = 1) { carUpdateService.deleteCar(99L) }
    }

    @Test
    fun `carUpdated subscription should emit updated cars`() {
        val car = CarDto(1L, "Tesla", "Model S", "Red", 2023, 60000)

        val flux = carGraphQLController.carUpdated()
        val carInput = CarInput("Tesla", "Model S", "Red", 2023, 60000)
        every { carUpdateService.updateCar(1L, carInput) } returns car
        carGraphQLController.updateCar(1L, carInput)

        StepVerifier.create(flux)
            .then { carGraphQLController.updateCar(1L, CarInput("Tesla", "Model S", "Red", 2023, 60000)) }
            .expectNext(car)
            .thenCancel()
            .verify()
    }

    @Test
    fun `carDeleted subscription should emit deleted cars`() {
        val car = CarDto(2L, "Honda", "Accord", "Blue", 2021, 28000)
        every { carUpdateService.deleteCar(2L) } returns car

        val flux = carGraphQLController.carDeleted()
        carGraphQLController.deleteCar(2L)

        StepVerifier.create(flux)
            .then { carGraphQLController.deleteCar(2L) }
            .expectNext(car)
            .thenCancel()
            .verify()
    }
}
