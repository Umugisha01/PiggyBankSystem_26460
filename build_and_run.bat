@echo off
echo Java Version:
java -version
echo.

echo Maven Version:
call mvn -v
echo.

echo Running Maven clean install with debug output...
call mvn clean install -e -X > maven_build.log 2>&1

echo Build log has been written to maven_build.log
type maven_build.log

echo.
echo If the build was successful, starting the application...
call mvn spring-boot:run
