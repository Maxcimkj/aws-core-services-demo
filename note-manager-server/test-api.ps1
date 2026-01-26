# Complete API Test Script - Ready to Execute
# Usage: .\test-api.ps1

$baseUrl = "http://localhost:8080/api/notes"

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
    $createdNote = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json" -Body $createBody
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
    $createdNote2 = Invoke-RestMethod -Uri $baseUrl -Method Post -ContentType "application/json" -Body $createBody2
    Write-Host "✓ Second note created successfully!" -ForegroundColor Green
    Write-Host "  ID: $($createdNote2.id)" -ForegroundColor Gray
    Write-Host ""
} catch {
    Write-Host "✗ Failed to create second note: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Get All Notes
Write-Host "3. Retrieving all notes..." -ForegroundColor Yellow
try {
    $allNotes = Invoke-RestMethod -Uri $baseUrl -Method Get
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
