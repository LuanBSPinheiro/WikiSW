# WikiSW - Galactic Records

WikiSW is an Android application that serves as a repository for Star Wars character information, utilizing the [SWAPI](https://swapi.info/) API.

## 🚀 Features

- **Character Directory**: Browse a list of all Star Wars characters.
- **Search**: Filter characters by name with real-time updates.
- **Favorites**: Mark characters as favorites for quick access.
- **Detailed Intelligence**: View detailed information about height, gender, planet of origin, and species.
- **Offline First**: All data is cached locally using Room for offline availability.

## 🏗 Architecture

The project follows **Clean Architecture** principles and is divided into three main layers:

### 1. Domain Layer
Contains the core business logic.
- **Entities**: Plain data classes like `Character`.
- **UseCases**: Granular classes following the Single Responsibility Principle (SRP) and using the `invoke` operator.
  - `GetCharactersUseCase`: Retrieves character lists with filters.
  - `RefreshCharactersUseCase`: Updates local data from the API.
  - `ToggleFavoriteUseCase`: Manages favorite status.

### 2. Data Layer
Handles data sourcing and persistence.
- **Repository Implementation**: Coordinates between `StarWarsApi` (Retrofit) and `CharacterDao` (Room).
- **Mappers**: Transforms DTOs and Entities into Domain models.

### 3. Presentation Layer
Implements the UI using **Jetpack Compose**.
- **MVVM Pattern**: `CharactersViewModel` manages the UI state and communicates with UseCases.
- **Reactive UI**: Uses Kotlin Flows and `collectAsState` for seamless UI updates.

## 🛠 Tech Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: Modern UI toolkit.
- **Koin**: Dependency injection.
- **Retrofit**: Networking.
- **Room**: Local persistence.
- **Coroutines & Flow**: Asynchronous programming.
- **Mockito**: Unit testing.

## 🧪 Testing

The project includes a comprehensive test suite:
- **Unit Tests**:
  - `CharactersViewModelTest`: Verifies UI state transitions.
  - `StarWarsRepositoryTest`: Ensures data coordination logic.
  - `CharacterMapperTest`: Validates data transformations.
  - `GetCharactersUseCaseTest`: Tests business logic isolation.

To run the tests:
```bash
./gradlew test
```

## 📜 SOLID Compliance

- **S (SRP)**: Each UseCase and Component has a single, well-defined responsibility.
- **O (OCP)**: The system is designed to be extendable without modifying existing business logic (e.g., adding new UseCases).
- **L (LSP)**: Interface implementations (like `StarWarsRepositoryImpl`) are interchangeable with their abstractions.
- **I (ISP)**: Interfaces are focused and not bloated with unnecessary methods.
- **D (DIP)**: High-level modules do not depend on low-level modules; both depend on abstractions (via Koin).
