@echo off
REM Create Note - Ready to Execute Batch Script
REM Usage: create-note.bat

curl -X POST http://localhost:8080/api/notes ^
  -H "Content-Type: application/json" ^
  -d "{\"creatorName\": \"John Doe\", \"creatorEmail\": \"john.doe@example.com\", \"content\": \"This is my first note. It contains important information.\"}"

pause
