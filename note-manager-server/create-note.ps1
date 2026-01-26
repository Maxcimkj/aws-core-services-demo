# Create Note - Ready to Execute PowerShell Script
# Usage: .\create-note.ps1

$uri = "http://localhost:8080/api/notes"
$body = @{
    creatorName = "John Doe"
    creatorEmail = "john.doe@example.com"
    content = "This is my first note. It contains important information."
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri $uri -Method Post -ContentType "application/json" -Body $body
    Write-Host "Note created successfully!" -ForegroundColor Green
    Write-Host "`nResponse:" -ForegroundColor Cyan
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error occurred:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    if ($_.ErrorDetails.Message) {
        Write-Host "Details: $($_.ErrorDetails.Message)" -ForegroundColor Red
    }
}
