if "%GAME_TO_RUN%"==""          ( set GAME_TO_RUN=mollymage)
if "%BOARD_URL%"==""            ( set BOARD_URL=http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000)
if "%PSEUDO_HERO_ELEMENTS%"=="" ( set PSEUDO_HERO_ELEMENTS=HERO,POTION_HERO,DEAD_HERO)

set ROOT=%CD%

if "%SKIP_TESTS%"=="" ( set SKIP_TESTS=true)

set CODE_PAGE=65001
chcp %CODE_PAGE%

set TOOLS=%ROOT%\.tools
set ARCH=%TOOLS%\7z\7za.exe

rem Set to true if you want to ignore jdk and maven installed on the system
if "%INSTALL_LOCALLY%"=="" ( set INSTALL_LOCALLY=true)

if "%INSTALL_LOCALLY%"=="true" ( set JAVA_HOME=)
if "%INSTALL_LOCALLY%"=="true" ( set MAVEN_HOME=)

if "%JAVA_HOME%"=="" ( set JAVA_HOME=%ROOT%\.jdk)
if "%MAVEN_HOME%"=="" ( set NO_MAVEN=true)
if "%NO_MAVEN%"=="true" ( set MAVEN_HOME=%ROOT%\.mvn)
if "%NO_MAVEN%"=="true" ( set MAVEN_OPTS=-Dmaven.repo.local=%MAVEN_HOME%\repository)
set MVNW=mvnw

echo off
echo        [44;93mJAVA_HOME=%JAVA_HOME%[0m
echo        [44;93mMAVEN_HOME=%MAVEN_HOME%[0m
echo        [44;93mMAVEN_OPTS=%MAVEN_OPTS%[0m
echo on

set ARCH_JDK=https://aka.ms/download-jdk/microsoft-jdk-11.0.11.9.1-windows-x64.zip
set ARCH_JDK_FOLDER=jdk-11.0.11+9

set PSEUDO_CLIENT_HOME=%ROOT%
set PSEUDO_RULES=%PSEUDO_CLIENT_HOME%\rules