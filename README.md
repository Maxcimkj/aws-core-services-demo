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

## 2. Deployment Automation

You can use the `deploy.bat` script in the root directory to automate building, uploading, and restarting the service on your EC2 instance.

**Prerequisites:**
- Ensure your `.pem` file is in the root directory.
- Edit `deploy.bat` to set the correct `EC2_USER`, `EC2_IP`, and `PEM_FILE` variables.

**Run deployment:**
```powershell
.\deploy.bat
```

---

## 3. Quick Start – Ready to Execute Commands

### Windows PowerShell (Recommended for Windows)

> **Auth required:** All HTTP calls must include a valid **Cognito access token** in an `Authorization: Bearer <token>` header. Obtain the token by signing in via the frontend (`https://note-manager.mekh.click/`) and inspecting the network requests or browser storage.

**Single Command – Create Note**
```powershell
$token = "<paste-access-token-here>"
Invoke-RestMethod -Uri "http://localhost:8080/api/notes" -Method Post -ContentType "application/json" -Headers @{ Authorization = "Bearer $token" } -Body '{"creatorName":"John Doe","creatorEmail":"john.doe@example.com","content":"This is my first note."}'
```

**Run Complete Test Script**
```powershell
.\test-api.ps1
```

### Prerequisites

- Spring Boot app running at `http://localhost:8080`
- PowerShell: 5.1+ or PowerShell Core
- curl: installed (common on Linux/Mac; Windows 10+ has it)

---

## 4. API Request Examples

This section shows how to call the Note Manager REST API with and without helper scripts. All examples assume you already have a valid **Cognito access token** and include it in an `Authorization: Bearer <access_token>` header.

### Create Note

**Endpoint:** `POST http://localhost:8080/api/notes`  
**Headers:**  
- `Content-Type: application/json`  
- `Authorization: Bearer <access_token>`

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
TOKEN="<paste-access-token-here>"
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
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
**Headers:** `Authorization: Bearer <access_token>`

**cURL:**
```bash
TOKEN="<paste-access-token-here>"
curl -X GET http://localhost:8080/api/notes -H "Authorization: Bearer $TOKEN"
```

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

## 5. Getting Started & Reference

**Note:** The original package name `com.example.note-manager` is invalid; the project uses `com.example.note_manager`.

### Reference documentation

- [Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/4.0.2/gradle-plugin)
- [Create an OCI image](https://spring.io/gradle-plugin/packaging-oci-image.html)
- [Spring Web](https://docs.spring.io/spring-boot/4.0.2/reference/web/servlet.html)

### Guides

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional links

- [Gradle Build Scans](https://scans.gradle.com#gradle)

---

## 6. AWS EC2 & CloudShell Quick Reference

### 6.1 Upload file to AWS CloudShell

1. Open CloudShell (icon in top-right of AWS Console).
2. Click **Actions → Upload file**.
3. Select your file (e.g., `key.pem` or `app.jar`) and click **Upload**.
4. The file will be in `/home/cloudshell-user/`.

### 6.2 Connect to EC2 via SSH from CloudShell

**Step A: Set key permissions (required for security)**

```bash
chmod 400 your-key-name.pem
```

**Step B: Run SSH**

```bash
ssh -i "your-key-name.pem" username@public-ip-address
```

```bash
ssh -i "admin-ec2.pem" ec2-user@13.61.23.46
```

Usernames: `ec2-user` (Amazon Linux), `ubuntu` (Ubuntu).

### 6.3 Copy files from CloudShell to EC2

From the CloudShell terminal:

```bash
scp -i "your-key-name.pem" local-file.txt username@public-ip-address:/home/username/
```

```bash
scp -i "admin-ec2.pem" note-manager-0.0.1-SNAPSHOT.jar ec2-user@13.61.23.46:/home/ec2-user/note-manager-0.0.1-SNAPSHOT.jar
```

Ensure port 22 is open in your EC2 Security Group for the CloudShell IP.

### 6.4 Manage myapp.service on EC2

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

### 6.5 Find the startup script for myapp.service

View the service configuration:

```bash
sudo systemctl cat myapp.service
```

Find the `ExecStart=` line for the path, e.g.:

`ExecStart=/usr/bin/java -jar /home/ec2-user/note-manager-0.0.1-SNAPSHOT.jar`

---

## 7. Current app AWS setup

1. **Public domain** – [mekh.click](https://mekh.click) bought on [Spaceship](https://www.spaceship.com) (registrar).
2. **Frontend (Amplify)** – AWS Amplify hosts the app on the mekh.click domain: [https://note-manager.mekh.click](https://note-manager.mekh.click).
3. **Load balancer & DNS** – Application Load Balancer on mekh.click; Route 53 with **mekh.click** hosted zone; ACM certificate for **\*.mekh.click** (HTTPS).
4. **Backend (EC2)** – Spring Boot JAR runs on an EC2 instance as **myapp.service** (enabled on startup). API is reached via the load balancer: [https://note-api.mekh.click/api/notes](https://note-api.mekh.click/api/notes).

### 7.1 Cognitio authentication ingeration
#### Frontend Authentication (Implicit Grant Flow)
Static HTML page redirects users to:
```
const authUrl = `https://${COGNITO.domain}/oauth2/authorize?response_type=token&client_id=${clientId}&redirect_uri=${redirectUri}&scope=openid+profile+email`;
window.location.href = authUrl;
```
#### Cognito redirect user to Login page (Implicit Grant Flow)
User enters email/ password registered in Cognito User pool

#### Direct Token Receipt
After user authentication, Cognito redirects back with tokens directly in the URL fragment
```
https://your-app.com/callback#access_token=eyJ...&id_token=eyJ...&token_type=Bearer&expires_in=3600

```
 Frontend Token Extraction
 ```
 function parseTokensFromCallback() {
    const hash = window.location.hash.substring(1);
    const params = new URLSearchParams(hash);
    return {
        accessToken: params.get('access_token'),
        idToken: params.get('id_token')
    };
}
 ```

#### Backend Configuration (Spring Boot)
```
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
        return http.build();
    }
}
```
Application Properties
```
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://cognito-idp.eu-north-1.amazonaws.com/eu-north-1_Oe4Ubp5pI
```

### 7.2 DynamoDb integration

The application uses AWS DynamoDB as the primary data store.

#### Configuration
- **`DynamoDbConfig.java`**: Configures the `DynamoDbClient` and `DynamoDbEnhancedClient` beans required for AWS SDK interaction.
- **`application.properties`**: Defines the AWS region:
  ```properties
  aws.region=eu-north-1
  ```

#### Table Management & Operations
- **`NoteRepository.java`**: Handles all database interactions.
  - **Initialization**: Uses `@PostConstruct` to check if the `Notes` table exists. If not, it automatically creates the table with provisioned throughput (5 read/write units).
  - **Operations**: Provides `save()` (PutItem) and `findAll()` (Scan) methods using the Enhanced Client.

#### Access & Security
The application does not store any AWS credentials (like access keys or secrets) explicitly. Instead, it relies on the **IAM Role** attached to the EC2 instance. The AWS SDK automatically uses the `DefaultCredentialsProvider`, which securely retrieves temporary credentials from the EC2 instance metadata service based on the assigned role and policies.

To grant the necessary permissions, attach an IAM policy to the EC2 instance's role. This policy allows the application to:
- **Create Tables**: Permission to create the `Notes` table if it doesn't exist.
- **Read/Write Data**: Permission to perform CRUD operations (`PutItem`, `GetItem`, `Scan`, etc.) on the `Notes` table.

**IAM Policy:**
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "DynamoDBTableCreationAndManagement",
            "Effect": "Allow",
            "Action": [
                "dynamodb:CreateTable",
                "dynamodb:DescribeTable",
                "dynamodb:ListTables",
                "dynamodb:DescribeLimits",
                "dynamodb:DescribeTimeToLive"
            ],
            "Resource": "*"
        },
        {
            "Sid": "DynamoDBTableOperations",
            "Effect": "Allow",
            "Action": [
                "dynamodb:PutItem",
                "dynamodb:GetItem",
                "dynamodb:UpdateItem",
                "dynamodb:DeleteItem",
                "dynamodb:Scan",
                "dynamodb:Query",
                "dynamodb:BatchGetItem",
                "dynamodb:BatchWriteItem",
                "dynamodb:ConditionCheckItem"
            ],
            "Resource": [
                "arn:aws:dynamodb:*:*:table/Notes",
                "arn:aws:dynamodb:*:*:table/Notes/index/*"
            ]
        }
    ]
}
```

### 7.3 Postgres Integration

The application supports PostgreSQL as a data store, hosted on AWS RDS.

- **Connection**: Uses standard JDBC connectivity with username and password authentication configured in `application.yml`.
- **Implementation**: Data access is implemented via Spring Data JPA. The application has permissions to create and update tables automatically.
- **Network Security**: To allow connectivity, configure the **Security Groups** for both the EC2 instance and the RDS instance:
    - **RDS Security Group**: Add an **Inbound Rule** allowing TCP traffic on port 5432 from the Security Group ID of the EC2 instance.
    - **EC2 Security Group**: Ensure **Outbound Rules** allow traffic to the RDS instance on port 5432.
- **Source Code**:
    - Repository: [`PostgresDbNoteRepository.java`](src/main/java/com/example/note_manager/repository/PostgresDbNoteRepository.java)
    - Service: [`PostgresDbNoteService.java`](src/main/java/com/example/note_manager/service/PostgresDbNoteService.java)

### 7.4 AWS Secrets Manager Integration

The application uses AWS Secrets Manager to securely retrieve RDS PostgreSQL credentials instead of storing them in application configuration files.

#### 7.4.1 AWS Secrets Manager Configuration

1. **Secret Structure**: 
   - Create a secret in AWS Secrets Manager containing a JSON object with all fields expected by the `RdsCredentials` class:
     ```json
     {
       "host": "your-rds-endpoint",
       "port": 5432,
       "dbname": "your-database-name",
       "username": "your-database-username",
       "password": "your-database-password"
     }
     ```

2. **IAM Policy for EC2 Instance**:
   - Attach an IAM policy to the EC2 instance role that allows reading secrets from Secrets Manager:
   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Effect": "Allow",
         "Action": [
           "secretsmanager:DescribeSecret",
           "secretsmanager:GetSecretValue",
           "secretsmanager:ListSecretVersionIds"
         ],
         "Resource": "*"
       },
       {
         "Effect": "Allow",
         "Action": [
           "kms:Decrypt"
         ],
         "Resource": "*",
         "Condition": {
           "StringEquals": {
             "kms:ViaService": [
               "secretsmanager.*.amazonaws.com"
             ]
           }
         }
       }
     ]
   }
   ```
   - This policy grants the EC2 instance permission to retrieve secret values and decrypt them using KMS if the secret is encrypted.

3. **Configuration**:
   - The secret identifier (ARN or name) is configured in `application.yml` under a property such as `aws.secrets.secretId`.
   - Actual database credentials (host, port, username, password) are never stored in `application.yml` or any other configuration file within the codebase.
   - Credentials are fetched at runtime from AWS Secrets Manager using the secret identifier.

#### 7.4.2 Implementation Components

The Secrets Manager integration involves three main components:

1. **`RdsCredentials`**:
   - A model class that maps to the JSON structure stored in AWS Secrets Manager.
   - Contains fields: `host`, `port`, `dbname`, `username`, `password`.
   - Used to deserialize the secret value retrieved from AWS Secrets Manager.

2. **`RdsSecretsService`**:
   - A Spring `@Service` component that handles communication with AWS Secrets Manager.
   - Reads the secret identifier (ARN or name) from `application.yml` (e.g., `aws.secrets.secretId`).
   - Uses the AWS SDK for Java (SecretsManagerClient) to fetch the secret value using the secret identifier.
   - Parses the JSON response into an `RdsCredentials` object.
   - Caches the credentials to avoid repeated API calls (implementation may vary).
   - Logs the retrieval process for monitoring and debugging.

3. **`RdsDataSourceConfig`**:
   - A Spring `@Configuration` class that creates the DataSource bean for database connections.
   - Injects `RdsSecretsService` to obtain database credentials at runtime.
   - Uses the retrieved credentials to configure and return a `DataSource` bean (typically HikariCP connection pool).
   - Ensures the application can establish database connections using the securely fetched credentials.

**Important**: While the secret identifier (e.g., `arn:aws:secretsmanager:region:account:secret:name`) is stored in `application.yml`, the actual database credentials (host, port, username, password) are never stored in configuration files. They are always fetched at runtime from AWS Secrets Manager.

#### 7.4.3 Interaction Flow

1. At application startup, Spring initializes the `RdsSecretsService` bean.
2. `RdsSecretsService` uses AWS SDK to call Secrets Manager API and retrieve the secret value.
3. The secret JSON is deserialized into an `RdsCredentials` object.
4. `RdsDataSourceConfig` injects `RdsSecretsService` and calls it to get `RdsCredentials`.
5. Using these credentials, `RdsDataSourceConfig` builds and returns a configured `DataSource` bean.
6. The `DataSource` is used by Spring Data JPA (`PostgresDbNoteRepository`) to establish database connections.

This approach ensures that database credentials remain secure, are centrally managed in AWS Secrets Manager, and can be rotated without modifying the application code.

### 7.5 SQS Integration

The application integrates with Amazon Simple Queue Service (SQS) to publish notifications whenever a new note is created, regardless of whether it is stored in PostgreSQL or DynamoDB.

#### 7.5.1 Configuration
- **Queue URL**: Defined in `application.yml` under `aws.sqs.queue-url`.
- **SQS Client**: Configured in `SqsConfig.java` using the `DefaultCredentialsProvider` (IAM Role).

#### 7.5.2 Implementation
- **`SqsService`**: A wrapper service that uses `SqsClient` and `ObjectMapper` to serialize note objects into JSON and send them to the configured queue.
- **Service Orchestration**: Both `PostgresDbNoteService` and `DynamoDbNoteService` inject `SqsService` and trigger `sendNoteNotification()` immediately after a successful database save.

#### 7.5.3 IAM Permissions
The`sqs:SendMessage` queue permission for EC2 IAM role:
```json
 {
  "Sid": "__sender_statement",
  "Effect": "Allow",
  "Principal": {
    "AWS": "arn:aws:iam::845573459200:role/EC2_Spring_boot_access"
  },
  "Action": "SQS:SendMessage",
  "Resource": "arn:aws:sqs:eu-north-1:845573459200:notes-queue"
}
```

---

*License: see `LICENSE` in the project root.*
