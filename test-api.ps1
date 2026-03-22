# Complete API Test Script - Ready to Execute
# Usage: .\test-api.ps1

$baseUrl = "http://localhost:8080/api/notes"
$token = "eyJraWQiOiJQXC9YRVwvOTdIVGEzSEZMVGVrcURvYnhiczU0ZVhpbXRKS3R3aUNPdEJET1k9IiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoicElsMThsckVIQk5Ja0tCZWRjdHIzQSIsInN1YiI6IjYwZWM4OTNjLTAwODEtNzA0MC03YTRiLThiNGEwNjcwOWQ5NiIsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS1ub3J0aC0xLmFtYXpvbmF3cy5jb21cL2V1LW5vcnRoLTFfT2U0VWJwNXBJIiwiY29nbml0bzp1c2VybmFtZSI6IjYwZWM4OTNjLTAwODEtNzA0MC03YTRiLThiNGEwNjcwOWQ5NiIsImF1ZCI6Im5xbzBjYnZlYjNlaGFobmxkcGhqZzk2bWsiLCJldmVudF9pZCI6IjE1NTJkYmNmLTY4Y2MtNDFjZi1iYWUzLTY2ZjEzNzc1YzkwYSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNzczNjA4MzcwLCJleHAiOjE3NzM2MTE5NzAsImlhdCI6MTc3MzYwODM3MCwianRpIjoiOGQ2Zjc3Y2EtY2I1My00MTY1LTkyMTctMmYyNmRkYmY1NmNkIiwiZW1haWwiOiJtYWtzaW1tZWtoQGdtYWlsLmNvbSJ9.kCa513gxzXXP2iuwrIGByanLiRncOFGthqM6RCUHolAE0tEm9bBpjTJYcbZ8S0PCfkHGl5Ufwcsp7JsXxq8i9H4DN1sdmoHnqsDm5ku6y-RToN-pb4aPDUJVgRflyDBTkRi5GhgKOeMdo48govOuq0aOMFUEY4XgJVgk10NR3THMj7XIpjMH_PVgSCeTMo4qNh-GToupVGOqLF8RYieAATyhVmbGZNu9Su1ZZnsTMhxP7WBu_doIBtXorRsXobM3SZoLh0GnSLq3DCICB17OXQKHiMkGh6ctI0lal2PRAwAfeWi4xhEMqZLvwCUl5EGjdwudccj5w-OIlnw-3IlFtA"

Write-Host "=== Note Manager API Test ===" -ForegroundColor Cyan
Write-Host ""

# Test 1: Create Note
Write-Host "1. Creating a note..." -ForegroundColor Yellow
$createBody = @{
    creatorName = "John Doe"
    creatorEmail = "john.doe@example.com"
    content = "This is my first note. It contains important information."
} | ConvertTo-Json

try {
    $createdNote = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json" -Headers @{ Authorization = "Bearer $token" } -Body $createBody
    Write-Host "✓ Note created successfully!" -ForegroundColor Green
    Write-Host "  ID: $($createdNote.id)" -ForegroundColor Gray
    Write-Host "  Creator: $($createdNote.creatorName)" -ForegroundColor Gray
    Write-Host ""
} catch {
    Write-Host "✗ Failed to create note: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Test 2: Create Another Note
Write-Host "2. Creating another note..." -ForegroundColor Yellow
$createBody2 = @{
    creatorName = "Jane Smith"
    creatorEmail = "jane.smith@example.com"
    content = "Another note with different content."
} | ConvertTo-Json

try {
    $createdNote2 = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json" -Headers @{ Authorization = "Bearer $token" } -Body $createBody2
    Write-Host "✓ Second note created successfully!" -ForegroundColor Green
    Write-Host "  ID: $($createdNote2.id)" -ForegroundColor Gray
    Write-Host ""
} catch {
    Write-Host "✗ Failed to create second note: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Get All Notes
Write-Host "3. Retrieving all notes..." -ForegroundColor Yellow
try {
    $allNotes = Invoke-RestMethod -Uri $baseUrl -Method Get -ContentType "application/json" -Headers @{ Authorization = "Bearer $token" }
    Write-Host "✓ Retrieved all notes successfully!" -ForegroundColor Green
    Write-Host "  Total notes: $($allNotes.Count)" -ForegroundColor Gray
    Write-Host ""
    Write-Host "All Notes:" -ForegroundColor Cyan
    $allNotes | ForEach-Object {
        Write-Host "  - [$($_.id)] $($_.creatorName) - $($_.content.Substring(0, [Math]::Min(50, $_.content.Length)))..." -ForegroundColor White
    }
} catch {
    Write-Host "✗ Failed to retrieve notes: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Test Complete ===" -ForegroundColor Cyan
