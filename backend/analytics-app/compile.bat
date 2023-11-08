@echo off
setlocal

:: Define the Maven version to use
set "MAVEN_VERSION=4.0.0"

:: Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    :: If Maven is not installed, download and install it
    echo Maven is not installed. Installing...
    bitsadmin.exe /transfer "Maven" "https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip" "%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip"
    tar.exe -xf "%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip" -C "%TEMP%"
    set "MAVEN_HOME=%TEMP%\apache-maven-%MAVEN_VERSION%"
    set "PATH=%MAVEN_HOME%\bin;%PATH%"
)

:: Maven commands
mvn wrapper:wrapper
mvn compile

endlocal