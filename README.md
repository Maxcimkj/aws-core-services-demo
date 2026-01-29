# Note Manager

## Spring Boot (Gradle)

**Run locally:**
```bash
cd note-manager-server && ./gradlew bootRun
```

**Build production JAR:**
```bash
cd note-manager-server && ./gradlew build
```

**Artifact:** `note-manager-server/build/libs/note-manager-0.0.1-SNAPSHOT.jar`

## API (cURL)

**Create note (POST):**
```bash
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d '{"creatorName": "John Doe", "creatorEmail": "john.doe@example.com", "content": "My note content"}'
```

**Get notes (GET):**
```bash
curl http://localhost:8080/api/notes
```
