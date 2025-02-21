
# Car GraphQL API

This project is a **GraphQL API** built using **Spring Boot** with **Kotlin**. It allows users to manage a collection of cars, providing CRUD operations like creating, updating, deleting, and querying car details. Additionally, the API supports **subscriptions** to listen for updates and deletions of cars in real time.

---

## Features

- **Queries**: Retrieve all cars or a single car by ID.
- **Mutations**: Add, update, and delete cars.
- **Subscriptions**: Real-time streaming of updated and deleted cars.
- **Input Types**: Ability to input car details like brand, model, year, color, and price.

---

## Technologies

- **Backend**: 
  - Spring Boot (2.x)
  - Spring GraphQL
  - Kotlin
  - Reactor (for reactive programming)
  
- **Database**:
  - In-memory database or PostgreSQL (as you implement it).

- **Testing**:
  - JUnit 5
  - MockK (for mocking services)

---

## Getting Started

### Prerequisites

- **JDK 17+**
- **Gradle** (for build management)

### Clone the repository

```bash
git clone https://github.com/yourusername/car-graphql-api.git
cd car-graphql-api
```

### Build and Run the Application

1. **Build the project**:

   ```bash
   ./gradlew build
   ```

2. **Run the application**:

   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080`.

---

## GraphQL Endpoints

- **Query**: 
  - `getAllCars`: Retrieve a list of all cars.
  - `getCarById(id: Long)`: Retrieve a car by its ID.

- **Mutation**:
  - `addCar(car: CarInput)`: Add a new car.
  - `updateCar(id: Long, car: CarInput)`: Update an existing car by ID.
  - `deleteCar(id: Long)`: Delete a car by ID.

- **Subscription**:
  - `carUpdated`: Stream real-time updates when a car is modified.
  - `carDeleted`: Stream real-time notifications when a car is deleted.

---

## GraphQL Schema

### CarDto

```graphql
type CarDto {
  id: Long!
  brand: String!
  model: String!
  color: String!
  year: Int!
  price: Int!
}
```

### CarInput

```graphql
input CarInput {
  brand: String!
  model: String!
  color: String!
  year: Int!
  price: Int!
}
```

---

## Example GraphQL Queries

### Query: Get all cars
```graphql
query {
  getAllCars {
    id
    brand
    model
    color
    year
    price
  }
}
```

### Query: Get car by ID
```graphql
query {
  getCarById(id: 1) {
    id
    brand
    model
    color
    year
    price
  }
}
```

### Mutation: Add a new car
```graphql
mutation {
  addCar(car: {brand: "Tesla", model: "Model 3", color: "White", year: 2023, price: 45000}) {
    id
    brand
    model
    color
    year
    price
  }
}
```

### Mutation: Update a car
```graphql
mutation {
  updateCar(id: 1, car: {brand: "Tesla", model: "Model S", color: "Red", year: 2023, price: 60000}) {
    id
    brand
    model
    color
    year
    price
  }
}
```

### Mutation: Delete a car
```graphql
mutation {
  deleteCar(id: 1) {
    id
    brand
    model
    color
    year
    price
  }
}
```

---

## Real-time Subscriptions

- **Listen for updated cars**:
  ```graphql
  subscription {
    carUpdated {
      id
      brand
      model
      color
      year
      price
    }
  }
  ```

- **Listen for deleted cars**:
  ```graphql
  subscription {
    carDeleted {
      id
      brand
      model
      color
      year
      price
    }
  }
  ```

---

## Unit Tests

This project includes unit tests for the GraphQL controllers using **JUnit 5** and **MockK** for mocking dependencies.

To run tests:

```bash
./gradlew test
```

---