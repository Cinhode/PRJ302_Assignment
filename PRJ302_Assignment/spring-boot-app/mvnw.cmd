:: Spring Boot Maven Wrapper Script for Windows ::

@echo off
setlocal

:: Maven Wrapper Configuration
set MAVEN_HOME=%~dp0\.mvn\wra
set MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=256m

:: Check for Maven installation
if not exist "%MAVEN_HOME%\bin\mvn" (
    echo "Maven is not installed. Please install Maven or use the Maven Wrapper."
    exit /b 1
)

:: Execute Maven command
"%MAVEN_HOME%\bin\mvn" %*

endlocal
exit /b 0