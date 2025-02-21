package org.cars.persistance.repositoory

import org.cars.persistance.entity.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface CarRepository : JpaRepository<Car, Long>