@echo off
echo ================================================
echo     Starting the tool to execute jBC routine
echo ================================================
REM === Set Java 8 path (update as needed) ===
set JAVA8_HOME=C:\R22\Temenos\java\jdk8
set PATH=%JAVA8_HOME%\bin;%PATH%

REM === Path to external libraries. Must include path where all jars of T24 are present and the jar which contain jBC routine to execute. ===
set EXT_LIBS=C:\R22\Temenos\jboss\modules\com\temenos\t24\main\t24lib\*;C:\R22\Temenos\TAFJ\data\tafj\jars\*

REM === Path to your runnable jar ===
set APP_JAR=C:\FLS\Utilities\jBC-executor.jar
echo Running application JAR: %APP_JAR%

REM === Run the application with external classpath ===
echo.
echo Launching application...
java -cp "%APP_JAR%;%EXT_LIBS%" -jar %APP_JAR%

echo.
echo Application exited with code %ERRORLEVEL%.
pause