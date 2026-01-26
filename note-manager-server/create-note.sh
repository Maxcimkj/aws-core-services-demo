#!/bin/bash

# Create Note - Ready to Execute Script
# Usage: ./create-note.sh

curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "creatorName": "John Doe",
    "creatorEmail": "john.doe@example.com",
    "content": "This is my first note. It contains important information."
  }' \
  -w "\n\nHTTP Status: %{http_code}\n" \
  -s | jq .
