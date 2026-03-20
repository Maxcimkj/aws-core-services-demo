@echo off
setlocal

:: --- Configuration ---
:: Update these variables with your specific details
set EC2_USER=ec2-user
set EC2_IP=13.61.23.46
set PEM_FILE=admin-ec2.pem
set JAR_PATH=note-manager-server\build\libs\note-manager-0.0.1-SNAPSHOT.jar
set REMOTE_DEST=/home/%EC2_USER%/note-manager-0.0.1-SNAPSHOT.jar
set SERVICE_FILE=myapp.service
set REMOTE_SERVICE_DEST=/home/%EC2_USER%/myapp.service

echo [1/4] Building project...
cd note-manager-server
call gradlew clean build -x test
if %errorlevel% neq 0 (
    echo Build failed!
    exit /b %errorlevel%
)
cd ..

echo [2/4] Copying files to EC2...
scp -i "%PEM_FILE%" "%JAR_PATH%" %EC2_USER%@%EC2_IP%:%REMOTE_DEST%
scp -i "%PEM_FILE%" "%SERVICE_FILE%" %EC2_USER%@%EC2_IP%:%REMOTE_SERVICE_DEST%
if %errorlevel% neq 0 (
    echo SCP failed!
    exit /b %errorlevel%
)

echo [3/4] Installing service...
ssh -i "%PEM_FILE%" %EC2_USER%@%EC2_IP% "sudo mv %REMOTE_SERVICE_DEST% /etc/systemd/system/myapp.service && sudo systemctl daemon-reload"
if %errorlevel% neq 0 (
    echo Service installation failed!
    exit /b %errorlevel%
)

echo [4/4] Restarting service...
ssh -i "%PEM_FILE%" %EC2_USER%@%EC2_IP% "sudo systemctl restart myapp.service"
if %errorlevel% neq 0 (
    echo Service restart failed!
    exit /b %errorlevel%
)

echo Deployment successful!
endlocal
