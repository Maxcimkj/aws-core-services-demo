@echo off
setlocal

:: --- Configuration ---
:: Update these variables with your specific details
set EC2_USER=ec2-user
set EC2_IP=13.61.23.46
set PEM_FILE=admin-ec2.pem
set JAR_PATH=..\build\libs\note-manager-0.0.1-SNAPSHOT.jar
set REMOTE_DEST=/home/%EC2_USER%/note-manager-0.0.1-SNAPSHOT.jar
set SERVICE_FILE=myapp.service
set REMOTE_SERVICE_DEST=/home/%EC2_USER%/myapp.service

echo [1/5] Building project...
call ..\gradlew -p .. clean build -x test --configuration-cache
if %errorlevel% neq 0 (
    echo Build failed!
    exit /b %errorlevel%
)

echo [2/5] Copying files to EC2...
scp -i "..\%PEM_FILE%" "%JAR_PATH%" %EC2_USER%@%EC2_IP%:%REMOTE_DEST%
scp -i "%SERVICE_FILE%" %EC2_USER%@%EC2_IP%:%REMOTE_SERVICE_DEST%
if %errorlevel% neq 0 (
    echo SCP failed!
    exit /b %errorlevel%
)

echo [3/5] Installing service...
ssh -i "..\%PEM_FILE%" %EC2_USER%@%EC2_IP% "sudo mv %REMOTE_SERVICE_DEST% /etc/systemd/system/myapp.service && sudo systemctl daemon-reload"
if %errorlevel% neq 0 (
    echo Service installation failed!
    exit /b %errorlevel%
)

echo [4/5] Cleaning up previous logs...
ssh -i "..\%PEM_FILE%" %EC2_USER%@%EC2_IP% "if [ -f /var/log/myapp.log ]; then sudo sh -c 'cat /dev/null > /var/log/myapp.log' && echo 'Log file cleared successfully.'; else echo 'Log file does not exist, continuing...'; fi"
if %errorlevel% neq 0 (
    echo Log cleanup failed!
    exit /b %errorlevel%
)

echo [5/5] Restarting service...
ssh -i "..\%PEM_FILE%" %EC2_USER%@%EC2_IP% "sudo systemctl restart myapp.service"
if %errorlevel% neq 0 (
    echo Service restart failed!
    exit /b %errorlevel%
)

echo Deployment successful!
endlocal
