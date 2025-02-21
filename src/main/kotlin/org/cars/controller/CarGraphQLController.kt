package org.cars.controller

import org.cars.api.dto.CarDto
import org.cars.api.input.CarInput
import org.cars.api.service.CarQueryService
import org.cars.api.service.CarUpdateService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks


@Controller
class CarGraphQLController(
    private val carQueryService: CarQueryService,
    private val carUpdateService: CarUpdateService,
) {

    private val updatedCarSink = Sinks.many().multicast().onBackpressureBuffer<CarDto>()
    private val deletedCarSink = Sinks.many().multicast().onBackpressureBuffer<CarDto>()

    @QueryMapping
    fun getAllCars(): List<CarDto> = carQueryService.listCars()

    @QueryMapping
    fun getCarById(@Argument id: Long): CarDto {
        return carQueryService.getCarById(id)
    }

    @MutationMapping
    fun addCar(
        @Argument car: CarInput
    ): CarDto {
        return carUpdateService.createCar(car)
    }

    @MutationMapping
    fun updateCar(
        @Argument id: Long,
        @Argument car: CarInput
    ): CarDto {
        return carUpdateService.updateCar(id = id, carInput = car)
            .also { updatedCarSink.tryEmitNext(it) }
    }

    @MutationMapping
    fun deleteCar(@Argument id: Long): CarDto? {
        val deletedCar = carUpdateService.deleteCar(id)
        if (deletedCar != null) {
            deletedCarSink.tryEmitNext(deletedCar)
        }
        return deletedCar
    }

    @SubscriptionMapping
    fun carUpdated(): Flux<CarDto> {
        return updatedCarSink.asFlux()
    }

    @SubscriptionMapping
    fun carDeleted(): Flux<CarDto> {
        return deletedCarSink.asFlux()
    }
}
