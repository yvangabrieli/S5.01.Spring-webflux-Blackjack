♠️ Blackjack API - Reactive Spring WebFlux

A fully reactive REST API for playing Blackjack, built with Spring Boot WebFlux and following Hexagonal Architecture (Ports & Adapters) principles. This project demonstrates advanced reactive programming patterns with hybrid persistence using MongoDB and MySQL.

📋 Table of Contents

Project Overview
Tech Stack
Architecture
Features
API Endpoints
Getting Started
Testing
Docker Deployment
Project Structure
Assignment Compliance
Contributing


🎯 Project Overview
This Blackjack API is a Spring Academy IT Academy final project (Sprint 5, Task 1) that implements a complete multiplayer Blackjack game with:

Reactive Architecture: Non-blocking, asynchronous processing with Spring WebFlux
Dual Database Strategy: MongoDB for game state, MySQL for player statistics
Domain-Driven Design: Clean separation of concerns with hexagonal architecture
Production-Ready: Comprehensive error handling, validation, and documentation

Game Rules Implemented

Standard Blackjack rules (dealer stands on 17)
Support for up to 5 players per game
Automatic dealer play after all players finish
Winner determination with pot distribution
Player balance tracking


🛠 Tech Stack
CategoryTechnologyLanguageJava 21FrameworkSpring Boot 3.4.1 (WebFlux)DatabasesMongoDB 6.0 (Reactive) + MySQL 8.0 (JPA)Build ToolMaven 3.9+ContainerizationDocker & Docker ComposeDocumentationOpenAPI 3 (Swagger UI)TestingJUnit 5, Mockito, WebTestClient

🏗 Architecture
This project follows Hexagonal Architecture (Ports & Adapters):
┌─────────────────────────────────────────────────────┐
│                   WEB LAYER                         │
│  Controllers │ DTOs │ Exception Handlers │ Mappers  │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              APPLICATION LAYER                      │
│     Use Cases │ Ports (Interfaces) │ Services       │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                DOMAIN LAYER                         │
│   Aggregates │ Entities │ Value Objects │ Services  │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│           INFRASTRUCTURE LAYER                      │
│   MongoDB Adapters │ MySQL Adapters │ Mappers       │
└─────────────────────────────────────────────────────┘
Key Design Patterns

Repository Pattern: Abstraction over data access
Aggregate Pattern: Game and Player as aggregate roots
Value Objects: Immutable Money, Card, Deck
Domain Services: Complex business rules (BlackjackDomainService)
DTO Pattern: Separation of domain and API models


✨ Features
Core Features (Level 1 - Assignment Requirements)
✅ Reactive API with Spring WebFlux
✅ Dual Database Persistence (MongoDB + MySQL)
✅ Global Exception Handling with proper HTTP status codes
✅ Unit & Integration Tests for controllers and services
✅ Swagger Documentation at /swagger-ui/index.html
Enhanced Features (Beyond Requirements)
🎯 Player Synchronization: MySQL as single source of truth for player data
🎯 Rich Game Responses: Includes player hands, scores, dealer state, and winner
🎯 Ranking System: Sorted by balance (extensible to wins)
🎯 Start Game Endpoint: Explicit game initialization with card dealing
🎯 Enhanced Card Display: Face cards shown as J/Q/K/A instead of values

🔌 API Endpoints
Base URL
http://localhost:8080
Game Management
MethodEndpointDescriptionRequest BodyPOST/gamesCreate a new gameNoneGET/games/{gameId}Get game details with player handsNonePOST/games/{gameId}/startStart game (deals cards)NonePOST/games/{gameId}/playersAdd player to game{"name": "string", "initialMoney": {"amount": 0}}POST/games/{gameId}/playMake a move (HIT/STAND){"playerId": "uuid", "moveType": "HIT"}DELETE/games/{gameId}Delete a gameNone
Player Management
MethodEndpointDescriptionRequest BodyGET/players/{playerId}Get player detailsNonePUT/players/{playerId}Update player name/balance{"name": "string", "money": {"amount": 0}}DELETE/players/{playerId}Delete a playerNone
Ranking
MethodEndpointDescriptionQuery ParamsGET/rankingGet player ranking by balancegameId (UUID)
Health Check
MethodEndpointDescriptionGET/healthAPI health status

🚀 Getting Started
Prerequisites

Java 21 or higher
Maven 3.9+
Docker & Docker Compose (for containerized deployment)

Local Development (Without Docker)

Clone the repository

bash   git clone https://github.com/yvangabrieli/S5.01.Spring-webflux-Blackjack.git
   cd S5.01.Spring-webflux-Blackjack

Start databases (MySQL & MongoDB)

bash   docker-compose up mysql mongo -d

Run the application

bash   ./mvnw spring-boot:run
```

4. **Access Swagger UI**
```
   http://localhost:8080/swagger-ui/index.html
Quick Start with Docker Compose (Recommended)
bash# Build and start all services
docker-compose up --build

# Access the API
open http://localhost:8080/swagger-ui/index.html

🧪 Testing
Run All Tests
bash./mvnw test
```

### Test Coverage

| Layer | Coverage | Test Files |
|-------|----------|------------|
| **Domain** | ✅ High | `BlackjackDomainServiceTest` |
| **Controllers** | ✅ Complete | `GameControllerTests`, `PlayerControllerTests`, `RankingControllerTests`, `HealthControllerTests` |
| **Integration** | ✅ Basic | `BlackjackApplicationTests` |

### Test Structure
```
src/test/java/
├── domain/service/
│   └── BlackjackDomainServiceTest.java
├── web/controllers/
│   ├── GameControllerTests.java
│   ├── PlayerControllerTests.java
│   ├── RankingControllerTests.java
│   └── HealthControllerTests.java
└── BlackjackApplicationTests.java

🐳 Docker Deployment
Docker Compose Services
yamlservices:
  mysql:      # Player data (persistent)
  mongo:      # Game state (persistent)
  api:        # Spring Boot application
Build & Deploy
bash# Build without cache
docker-compose build --no-cache

# Start services
docker-compose up -d

# View logs
docker logs blackjack-api -f

# Stop services
docker-compose down
Environment Variables
properties# MySQL
MYSQL_DATABASE=blackjack
MYSQL_USER=blackjack
MYSQL_PASSWORD=blackjack

# MongoDB
MONGO_HOST=blackjack-mongo
MONGO_PORT=27017

# Application
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=mongo,mysql
```

---

## 📁 Project Structure
```
blackjack/
├── src/main/java/cat/itacademy/.../blackjack/
│   ├── application/          # Use Cases & Ports
│   │   ├── game/
│   │   ├── player/
│   │   └── ranking/
│   ├── domain/               # Business Logic
│   │   ├── model/
│   │   │   ├── aggregates/   (Game, Player)
│   │   │   ├── entity/       (Dealer, Hand)
│   │   │   ├── valueobject/  (Card, Deck, Money, Score)
│   │   │   └── enums/        (GameStatus, MoveType)
│   │   ├── service/          (BlackjackDomainService)
│   │   └── exception/
│   ├── infrastructure/       # Adapters
│   │   └── persistence/
│   │       ├── mongodb/      (GameRepositoryMongoAdapter)
│   │       └── mysql/        (PlayerRepositoryJpaAdapter)
│   ├── web/                  # API Layer
│   │   ├── controllers/
│   │   ├── dto/
│   │   └── exception/        (GlobalExceptionHandler)
│   └── config/               (MongoDB, MySQL, OpenAPI)
├── src/test/                 # Tests
├── docker-compose.yml
├── Dockerfile
└── pom.xml

📚 Key Classes
Domain Aggregates

Game: Manages game state, player turns, dealer logic, and winner determination
Player: Tracks player balance, hand, and bets

Value Objects

Card: Immutable card with Suit and Rank
Deck: Shuffled deck of 52 cards
Money: Prevents negative balances, handles arithmetic
Score: Calculates Blackjack hand value (handles Ace as 1 or 11)

Domain Services

BlackjackDomainService: Encapsulates game rules (dealer strategy, winner determination)

Adapters

GameRepositoryMongoAdapter: Stores/retrieves games from MongoDB, hydrates players from MySQL
PlayerRepositoryJpaAdapter: CRUD operations for players in MySQL


🎓 Assignment Compliance
Level 1 Requirements ✅
RequirementStatusImplementationReactive Application (WebFlux)✅Mono<>, Flux<> used throughoutMongoDB Configuration✅MongoReactiveConfig with UUID supportMySQL Configuration✅MySqlConfig with JPA repositoriesGlobal Exception Handler✅GlobalExceptionHandler with proper HTTP codesUnit Tests (Controller + Service)✅5+ test classes with MockitoSwagger Documentation✅OpenAPI 3 at /swagger-ui/index.html
Level 2 Requirements ✅
RequirementStatusFilesDockerfile✅Dockerfile.dockerignore✅.dockerignoreDocker Compose✅docker-compose.yml
Level 3 (Optional) 🚧

GitHub Actions for CI/CD: Not implemented
Cloud Deployment (Render/Railway): Not implemented


🎮 How to Play
1. Create a Game
bashPOST /games
Response:
json{
  "gameId": "uuid",
  "status": "NOT_STARTED",
  "players": [],
  "dealer": null
}
2. Add Players
bashPOST /games/{gameId}/players
Content-Type: application/json

{
  "name": "Alice",
  "initialMoney": {"amount": 100}
}
3. Start the Game
bashPOST /games/{gameId}/start
Cards are dealt to all players and the dealer.
4. Players Make Moves
bashPOST /games/{gameId}/play
Content-Type: application/json

{
  "playerId": "player-uuid",
  "moveType": "HIT"  // or "STAND"
}
5. Game Finishes Automatically
When all players STAND or BUST:

Dealer plays (hits until 17+)
Winner determined
Pot distributed
status becomes FINISHED

6. Check Winner
bashGET /games/{gameId}
Response includes:
json{
  "winnerId": "player-uuid",
  "status": "FINISHED",
  "players": [...],
  "dealer": {...}
}

🔧 Configuration
application.properties
properties# Application
spring.application.name=blackjack
spring.profiles.active=mongo,mysql

# MySQL
spring.datasource.url=jdbc:mysql://blackjack-mysql:3306/blackjack
spring.datasource.username=blackjack
spring.datasource.password=blackjack
spring.jpa.hibernate.ddl-auto=update

# MongoDB
spring.data.mongodb.uri=mongodb://blackjack-mongo:27017/blackjack
spring.data.mongodb.uuid-representation=standard

📊 Database Schema
MySQL (Players)
sqlCREATE TABLE players (
  id BINARY(16) PRIMARY KEY,
  name VARCHAR(255),
  balance DECIMAL(19,2)
);

CREATE TABLE hands (
  id BIGINT AUTO_INCREMENT PRIMARY KEY
);
MongoDB (Games)
javascript{
  _id: UUID,
  status: "NOT_STARTED" | "IN_PROGRESS" | "FINISHED",
  playerIds: [UUID],
  playerHands: {
    "uuid": [CardDocument]
  },
  deck: [CardDocument],
  dealerHand: [CardDocument],
  currentPlayerIndex: Number,
  winnerId: UUID,
  potAmount: Number,
  createdAt: Timestamp,
  updatedAt: Timestamp
}

🐛 Troubleshooting
Common Issues
Problem: UnsatisfiedDependencyException on startup
Solution: Ensure both mongo and mysql profiles are active
Problem: Players show empty hands
Solution: Call /games/{id}/start to deal cards before playing
Problem: Tests fail with context loading error
Solution: Run ./mvnw clean test or check @TestPropertySource excludes

🤝 Contributing
This is an academic project, but suggestions are welcome!

Fork the repository
Create a feature branch (git checkout -b feature/amazing-feature)
Commit your changes (git commit -m 'Add amazing feature')
Push to the branch (git push origin feature/amazing-feature)
Open a Pull Request


📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Author
Yvan Gabrieli

GitHub: @yvangabrieli
Project: S5.01-Spring-webflux-Blackjack


🙏 Acknowledgments

IT Academy - Spring Boot training program
Spring Community - Excellent reactive programming documentation
Stack Overflow - Countless debugging sessions


📈 Future Enhancements

 Win/loss tracking for players
 Betting system with pot management
 Multi-deck support
 WebSocket for real-time game updates
 Player authentication & sessions
 Game replay functionality
 Advanced statistics dashboard


Built with ❤️ using Spring WebFlux
