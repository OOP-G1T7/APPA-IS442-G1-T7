@echo off
setlocal

:: Define the Maven version to use
set "MAVEN_VERSION=3.9.5"

:: Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    :: If Maven is not installed, attempt to download and install it using wget
    echo Maven is not installed. Attempting to download with wget...
    wget "https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip" -O "%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip"
    
    :: Check the exit code of wget to determine success or failure
    if %errorlevel% neq 0 (
        :: If wget fails, download Maven using PowerShell
        echo wget failed. Downloading with PowerShell...
        powershell -Command "Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip'"

        :: Extract the downloaded Maven distribution using PowerShell
        powershell -Command "Expand-Archive -Path '%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip' -DestinationPath '%TEMP%'"
        
        set "MAVEN_HOME=%TEMP%\apache-maven-%MAVEN_VERSION%"
        set "PATH=%MAVEN_HOME%\bin;%PATH%"

    ) else {
        :: Extract the downloaded Maven distribution
        tar.exe -xf "%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip" -C "%TEMP%"
        
        set "MAVEN_HOME=%TEMP%\apache-maven-%MAVEN_VERSION%"
        set "PATH=%MAVEN_HOME%\bin;%PATH%"
    }
)

:: Maven commands
mvn clean compile

endlocal