# Note Manager

Single documentation for the Note Manager project. License is kept in the root `LICENSE` file.

---

## 1. Spring Boot (Gradle)

**Run locally:**
```bash
cd note-manager-server && ./gradlew bootRun
```

**Build production JAR:**
```bash
cd note-manager-server && ./gradlew build
```

**Artifact:** `note-manager-server/build/libs/note-manager-0.0.1-SNAPSHOT.jar`

---

## 2. Quick Start – Ready to Execute Commands

### Windows PowerShell (Recommended for Windows)

**Single Command – Create Note**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/notes" -Method Post -ContentType "application/json" -Body '{"creatorName":"John Doe","creatorEmail":"john.doe@example.com","content":"This is my first note."}'
```

**Run Complete Test Script**
```powershell
.\test-api.ps1
```

**Run Simple Create Script**
```powershell
.\create-note.ps1
```

### Windows Command Prompt (CMD)

**Single Command – Create Note**
```cmd
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d "{\"creatorName\":\"John Doe\",\"creatorEmail\":\"john.doe@example.com\",\"content\":\"This is my first note.\"}"
```

**Run Batch Script**
```cmd
create-note.bat
```

### Linux/Mac/Git Bash

**Single Command – Create Note**
```bash
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d '{"creatorName":"John Doe","creatorEmail":"john.doe@example.com","content":"This is my first note."}'
```

**Run Shell Script**
```bash
chmod +x create-note.sh
./create-note.sh
```

### VS Code / IntelliJ IDEA

Open `create-note.http` and click **Send Request** (requires REST Client extension).

### All Available Scripts

1. **create-note.ps1** – PowerShell (Windows)
2. **create-note.sh** – Bash (Linux/Mac)
3. **create-note.bat** – Batch (Windows CMD)
4. **create-note.http** – HTTP file for REST Client extensions
5. **test-api.ps1** – Full test (create notes + list all)

### Prerequisites

- Spring Boot app running at `http://localhost:8080`
- PowerShell: 5.1+ or PowerShell Core
- curl: installed (common on Linux/Mac; Windows 10+ has it)

---

## 3. API Request Examples

### Create Note

**Endpoint:** `POST http://localhost:8080/api/notes`  
**Headers:** `Content-Type: application/json`

The body may include only the fields you provide. `id` and `created_at` are set by the service.

**Request body example:**
```json
{
  "creatorName": "John Doe",
  "creatorEmail": "john.doe@example.com",
  "content": "This is my first note. It contains important information."
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "creatorName": "John Doe",
    "creatorEmail": "john.doe@example.com",
    "content": "This is my first note. It contains important information."
  }'
```

**PowerShell:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/notes" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{
    "creatorName": "John Doe",
    "creatorEmail": "john.doe@example.com",
    "content": "This is my first note. It contains important information."
  }'
```

**Response:** `201 Created`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "creatorName": "John Doe",
  "creatorEmail": "john.doe@example.com",
  "content": "This is my first note. It contains important information.",
  "created_at": "2026-01-25T10:30:45.123456"
}
```

### Get All Notes

**Endpoint:** `GET http://localhost:8080/api/notes`

**cURL:** `curl -X GET http://localhost:8080/api/notes`  
**PowerShell:** `Invoke-RestMethod -Uri "http://localhost:8080/api/notes" -Method Get`

**Response:** `200 OK` – JSON array of note objects (same shape as above).

### More body examples

**Simple:**
```json
{
  "creatorName": "Alice Johnson",
  "creatorEmail": "alice@example.com",
  "content": "Meeting notes from today's standup."
}
```

**Long content:**
```json
{
  "creatorName": "Bob Williams",
  "creatorEmail": "bob.williams@example.com",
  "content": "This is a longer note with multiple paragraphs. It can contain detailed information, lists, and formatted text."
}
```

**Minimal:**
```json
{
  "creatorName": "Charlie Brown",
  "creatorEmail": "charlie@example.com",
  "content": "Quick reminder"
}
```

---

## 4. Getting Started & Reference

**Note:** The original package name `com.example.note-manager` is invalid; the project uses `com.example.note_manager`.

### Reference documentation

- [Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/4.0.2/gradle-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/4.0.2/gradle-plugin/packaging-oci-image.html)
- [Spring Web](https://docs.spring.io/spring-boot/4.0.2/reference/web/servlet.html)

### Guides

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional links

- [Gradle Build Scans](https://scans.gradle.com#gradle)

---

## 5. AWS EC2 & CloudShell Quick Reference

### 5.1 Upload file to AWS CloudShell

1. Open CloudShell (icon in top-right of AWS Console).
2. Click **Actions → Upload file**.
3. Select your file (e.g., `key.pem` or `app.jar`) and click **Upload**.
4. The file will be in `/home/cloudshell-user/`.

### 5.2 Connect to EC2 via SSH from CloudShell

**Step A: Set key permissions (required for security)**

```bash
chmod 400 your-key-name.pem
```

**Step B: Run SSH**

```bash
ssh -i "your-key-name.pem" username@public-ip-address
```

Usernames: `ec2-user` (Amazon Linux), `ubuntu` (Ubuntu).

### 5.3 Copy files from CloudShell to EC2

From the CloudShell terminal:

```bash
scp -i "your-key-name.pem" local-file.txt username@public-ip-address:/home/username/
```

Ensure port 22 is open in your EC2 Security Group for the CloudShell IP.

### 5.4 Manage myapp.service on EC2

After logging into the EC2 instance:

**Stop the service:**

```bash
sudo systemctl stop myapp.service
```

**Disable auto-start on boot:**

```bash
sudo systemctl disable myapp.service
```

**Enable auto-start on boot:**

```bash
sudo systemctl enable myapp.service
```

**Start the service:**

```bash
sudo systemctl start myapp.service
```

### 5.5 Find the startup script for myapp.service

View the service configuration:

```bash
sudo systemctl cat myapp.service
```

Find the `ExecStart=` line for the path, e.g.:

`ExecStart=/usr/bin/java -jar /home/ec2-user/note-manager-0.0.1-SNAPSHOT.jar`

---

## 6. Current app AWS setup

1. **Public domain** – [mekh.click](https://mekh.click) bought on [Spaceship](https://www.spaceship.com) (registrar).
2. **Frontend (Amplify)** – AWS Amplify hosts the app on the mekh.click domain: [https://note-manager.mekh.click](https://note-manager.mekh.click).
3. **Load balancer & DNS** – Application Load Balancer on mekh.click; Route 53 with **mekh.click** hosted zone; ACM certificate for **\*.mekh.click** (HTTPS).
4. **Backend (EC2)** – Spring Boot JAR runs on an EC2 instance as **myapp.service** (enabled on startup). API is reached via the load balancer: [https://note-api.mekh.click/api/notes](https://note-api.mekh.click/api/notes).

---

*License: see `LICENSE` in the project root.*
