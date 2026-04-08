# Haven Backend

Backend API for Haven, a privacy-focused social matching platform.

## Tech Stack

- Java
- Spring Boot
- Spring Security
- PostgreSQL
- WebSockets (STOMP)

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL (local or hosted)

## Configuration

The backend is configured through `src/main/resources/application.properties`.

### Database Connection
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/haven
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### JWT Configuration
```properties
haven.jwt.secret=your_jwt_secret_key
```

### CORS Settings
CORS is configured in `SecurityConfig.java` to allow requests from `http://localhost:5173` (frontend).

## Running Locally

```bash
cd haven-backend
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`.

## API Endpoints

### Authentication
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/auth/register` | POST | Register a new user |
| `/auth/login` | POST | Login and receive JWT token |

### Profile
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/profile` | GET | Get current user's profile |
| `/profile` | PUT | Update current user's profile |
| `/profile/status` | GET | Check if profile is complete |
| `/profile/user/{userId}` | GET | Get another user's profile (for matches) |
| `/profile/photo` | POST | Upload profile photo |

### Discovery
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/discover` | GET | Get discovery feed (paginated) |
| `/discover/{userId}` | GET | Get discovery feed for specific user |

### Swipe & Match
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/swipe` | POST | Swipe left or right on a profile |
| `/matches` | GET | Get all matches for current user |

### Chat
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/messages/{matchId}` | GET | Get chat messages for a match |
| `/messages/{messageId}/seen` | PUT | Mark message as seen |
| `/ws` | WebSocket | STOMP WebSocket endpoint for real-time chat |

## Database

- PostgreSQL database (named `haven` or similar)
- Hibernate auto-creates tables on startup (configured via `spring.jpa.hibernate.ddl-auto=update`)
- Key tables: users, profiles, swipes, matches, messages

## Security

- JWT-based authentication
- Tokens include user email as the principal
- CORS configured to allow frontend origin (`localhost:5173`)
- Static resources (`/uploads/**`) are publicly accessible
- All other endpoints require valid JWT token

## Troubleshooting

### Database Connection Failed
- Ensure PostgreSQL is running
- Verify database name, username, and password in application.properties
- Check that the database exists on your PostgreSQL server

### CORS Errors
- Ensure CORS is configured in SecurityConfig.java to allow `http://localhost:5173`
- Check that frontend is making requests from the correct origin

### JWT Validation Errors
- Verify JWT secret key is set in application.properties
- Check that token hasn't expired (default: 24 hours)
- Ensure token is sent in the Authorization header: `Bearer <token>`

### WebSocket Connection Fails
- Verify `/ws` endpoint is accessible (should not require authentication for handshake)
- Check that STOMP client is connecting to `ws://localhost:8080/ws`
- Ensure CORS allows WebSocket connections

### Profile Photo Upload Fails
- Check that `uploads` folder exists and is writable
- Verify file size is under any configured limits
- Ensure file type is allowed (jpg, png, etc.)

### Users Not Appearing in Discovery
- Verify users have complete profiles (age and gender set)
- Check that swipes are being recorded correctly
- Ensure users haven't already been swiped on

---

## Additional Notes

- The backend runs on port 8080 by default
- JWT tokens expire after 24 hours
- Profile photos are stored locally in the `uploads` folder
- WebSocket uses STOMP protocol for real-time messaging
- Hibernate automatically creates and updates database schema