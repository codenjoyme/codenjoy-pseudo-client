echo off
call lib :color Setup variables...
echo on

if "%GAME_TO_RUN%"==""  ( set GAME_TO_RUN=mollymage)
if "%BOARD_URL%"==""    ( set BOARD_URL=http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000)
if "%PSEUDO_RULES%"=="" ( set PSEUDO_RULES=.\rules\%GAME_TO_RUN%)

set ROOT=%CD%

if "%SKIP_TESTS%"==""   ( set SKIP_TESTS=true)

set CODE_PAGE=65001
chcp %CODE_PAGE%

set TOOLS=%ROOT%\.tools
set ARCH=%TOOLS%\7z\7za.exe

rem Set to true if you want to ignore jdk and maven installation on the system
if "%INSTALL_LOCALLY%"==""     ( set INSTALL_LOCALLY=true)

if "%INSTALL_LOCALLY%"=="true" ( set JAVA_HOME=)
if "%INSTALL_LOCALLY%"=="true" ( set MAVEN_HOME=)

if "%JAVA_HOME%"==""    ( set JAVA_HOME=%ROOT%\.jdk)
if "%MAVEN_HOME%"==""   ( set NO_MAVEN=true)
if "%NO_MAVEN%"=="true" ( set MAVEN_HOME=%ROOT%\.mvn)
if "%NO_MAVEN%"=="true" ( set MAVEN_USER_HOME=%ROOT%\.mvn)
if "%NO_MAVEN%"=="true" ( set MAVEN_OPTS=-Dmaven.repo.local=%MAVEN_HOME%\repository)

set MVNW=%ROOT%\mvnw
set MVNW_VERBOSE=false
set JAVA=%JAVA_HOME%\bin\java

echo off
call lib :color JAVA_HOME=%JAVA_HOME%
call lib :color MAVEN_HOME=%MAVEN_HOME%
call lib :color MAVEN_OPTS=%MAVEN_OPTS%
call lib :color MAVEN_USER_HOME=%MAVEN_USER_HOME%
call lib :color MVNW_VERBOSE=%MVNW_VERBOSE%
echo on

set ARCH_URL=https://aka.ms/download-jdk/microsoft-jdk-11.0.11.9.1-windows-x64.zip
set ARCH_FOLDER=jdk-11.0.11+9

set PSEUDO_CLIENT_HOME=%ROOT%