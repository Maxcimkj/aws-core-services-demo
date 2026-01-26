# Quick Start - Ready to Execute Commands

## Windows PowerShell (Recommended for Windows)

### Single Command - Create Note
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/notes" -Method Post -ContentType "application/json" -Body '{"creatorName":"John Doe","creatorEmail":"john.doe@example.com","content":"This is my first note."}'
```

### Run Complete Test Script
```powershell
.\test-api.ps1
```

### Run Simple Create Script
```powershell
.\create-note.ps1
```

## Windows Command Prompt (CMD)

### Single Command - Create Note
```cmd
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d "{\"creatorName\":\"John Doe\",\"creatorEmail\":\"john.doe@example.com\",\"content\":\"This is my first note.\"}"
```

### Run Batch Script
```cmd
create-note.bat
```

## Linux/Mac/Git Bash

### Single Command - Create Note
```bash
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d '{"creatorName":"John Doe","creatorEmail":"john.doe@example.com","content":"This is my first note."}'
```

### Run Shell Script
```bash
chmod +x create-note.sh
./create-note.sh
```

## VS Code / IntelliJ IDEA

Open `create-note.http` file and click "Send Request" button (requires REST Client extension)

## All Available Scripts

1. **create-note.ps1** - PowerShell script for Windows
2. **create-note.sh** - Bash script for Linux/Mac
3. **create-note.bat** - Batch script for Windows CMD
4. **create-note.http** - HTTP file for REST Client extensions
5. **test-api.ps1** - Complete test suite (creates notes and retrieves all)

## Prerequisites

- Spring Boot application must be running on `http://localhost:8080`
- For PowerShell scripts: PowerShell 5.1+ or PowerShell Core
- For curl commands: curl must be installed (usually pre-installed on Linux/Mac, available on Windows 10+)
