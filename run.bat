@echo off

rem red *;91
rem green *;92
rem yellow *;93
rem blue *;94
rem pink *;95
rem light blue *;96
rem purple *;97
rem black background 40;*
rem dark yellow background 43;*
rem purple background 45;*
rem blue background 44;*
set CL_HEADER=43;93
set CL_COMMAND=40;96
set CL_QUESTION=45;97
set CL_INFO=44;93

call :settings

set OPTION=%1

:start
    if "%OPTION%"=="" (
        call :ask_option
    )
    call :%OPTION%
    goto :restart

:restart
    set OPTION=
    goto :start

:eval_echo
    set input=%~1%
    call set command=%%input:`="%%
    call :color "%CL_COMMAND%" "%input%"
    call %command%

    goto :eof

:ask_option
    call :color "%CL_QUESTION%" "What would you like to do: [d]ownload env, [b]uild, [t]est, [r]un or [q]uit?"
    set /p CODE=
    if "%CODE%"=="d" set OPTION=download
    if "%CODE%"=="b" set OPTION=build
    if "%CODE%"=="t" set OPTION=test
    if "%CODE%"=="r" set OPTION=run
    if "%CODE%"=="q" exit

    goto :eof

:read_env
    echo Reading enviromnent from .env file
    FOR /F "tokens=*" %%i in ('type .env') do (
        SET %%i
        call :color "%CL_INFO%" "%%i"
    )

    goto :eof

:print_color
	call :color "%CL_COMMAND%" "%*"
	call %* > %TOOLS%\out
	for /f "tokens=*" %%s in (%TOOLS%\out) do (
         call :color "%CL_INFO%" "%%s"
    )
    del /Q %TOOLS%\out

    goto :eof

:color
    set color=%~1%
    set message=%~2%
    echo [%color%m%message%[0m
    echo.

    goto :eof

:ask
    call :color "%CL_QUESTION%" "Press any key to continue"
    pause >nul

    goto :eof

:sep
    call :color "%CL_COMMAND%" "---------------------------------------------------------------------------------------"

    goto :eof

:download_file
    set url=%~1%
    set file=%~2%
    call :color "%CL_COMMAND%" "Downloading '%url%'"
    call :color "%CL_COMMAND%" "       into '%file%'"
    powershell -command "& { set-executionpolicy remotesigned -s currentuser; [System.Net.ServicePointManager]::SecurityProtocol = 3072 -bor 768 -bor 192 -bor 48; $client=New-Object System.Net.WebClient; $client.Headers.Add([System.Net.HttpRequestHeader]::Cookie, 'oraclelicense=accept-securebackup-cookie'); $client.DownloadFile('%url%','%file%') }"

    goto :eof

:install
    call :eval_echo "cd %ROOT%"
    call :eval_echo "set DEST=%~1"
    call :eval_echo "set URL=%~2"
    call :eval_echo "set FOLDER=%~3"
    IF EXIST %TOOLS%\%DEST%.zip (
        call :eval_echo "del /Q %TOOLS%\%DEST%.zip"
    )
    call :download_file "%URL%" "%TOOLS%\%DEST%.zip"
    call :eval_echo "rd /S /Q %TOOLS%\..\.%DEST%"
    if "%FOLDER%"=="" (
        call :eval_echo "%ARCH% x -y -o%TOOLS%\..\.%DEST% %TOOLS%\%DEST%.zip"
    ) ELSE (
        call :eval_echo "%ARCH% x -y -o%TOOLS%\.. %TOOLS%\%DEST%.zip"
        call :eval_echo "timeout 2"
        call :eval_echo "rename %TOOLS%\..\%FOLDER% .%DEST%"
    )
    call :eval_echo "cd %ROOT%"

    goto :eof

:settings
    call :color "%CL_HEADER%" "Setup variables..."

    call :read_env

    set ROOT=%CD%

    if "%SKIP_TESTS%"==""  ( set SKIP_TESTS=true)

    set CODE_PAGE=65001
    chcp %CODE_PAGE%

    set TOOLS=%ROOT%\.tools
    set ARCH=%TOOLS%\7z\7za.exe

    rem Set to true if you want to ignore go installation on the system
    if "%INSTALL_LOCALLY%"==""     ( set INSTALL_LOCALLY=true)

    if "%INSTALL_LOCALLY%"=="true" ( set JAVA_HOME=)
    if "%INSTALL_LOCALLY%"=="true" ( set MAVEN_HOME=)

    if "%JAVA_HOME%"==""    ( set NO_JAVA=true)
    if "%NO_JAVA%"=="true"  ( set JAVA_HOME=%ROOT%\.jdk)
    if "%NO_JAVA%"=="true"  ( set PATH=%JAVA_HOME%\bin;%PATH%)

    if "%MAVEN_HOME%"==""   ( set NO_MAVEN=true)
    if "%NO_MAVEN%"=="true" ( set MAVEN_HOME=%ROOT%\.mvn)
    if "%NO_MAVEN%"=="true" ( set MAVEN_USER_HOME=%MAVEN_HOME%)
    if "%NO_MAVEN%"=="true" ( set MAVEN_OPTS=-Dmaven.repo.local=%MAVEN_HOME%\repository)

    set MVNW=%ROOT%\mvnw
    set MVNW_VERBOSE=false
    set JAVA=%JAVA_HOME%\bin\java

    echo Language enviromnent variables
    call :color "%CL_INFO%" "PATH=%PATH%"
    call :color "%CL_INFO%" "JAVA_HOME=%JAVA_HOME%"
    call :color "%CL_INFO%" "MAVEN_HOME=%MAVEN_HOME%"
    call :color "%CL_INFO%" "MAVEN_OPTS=%MAVEN_OPTS%"
    call :color "%CL_INFO%" "MAVEN_USER_HOME=%MAVEN_USER_HOME%"
    call :color "%CL_INFO%" "MVNW_VERBOSE=%MVNW_VERBOSE%"

    set ARCH_URL=https://aka.ms/download-jdk/microsoft-jdk-11.0.11.9.1-windows-x64.zip
    set ARCH_FOLDER=jdk-11.0.11+9

    goto :eof

:download
    call :color "%CL_HEADER%" "Installing..."

    if     "%INSTALL_LOCALLY%"=="true" call :install jdk %ARCH_URL% %ARCH_FOLDER%
    if NOT "%INSTALL_LOCALLY%"=="true" call :color "%CL_INFO%" "The environment installed on the system is used"

    call :print_color %MVNW% -v

    goto :eof

:build
    call :color "%CL_HEADER%" "Building client..."

    call :print_color %MVNW% -v

    rem install jar to maven local repo
    call :eval_echo "%MVNW% clean install -DskipTests=%SKIP_TESTS%"

    rem create executable jar
    call :eval_echo "%MVNW% compile assembly:single -Pjar-with-dependencies -DskipTests=%SKIP_TESTS%"

    call :eval_echo "copy %ROOT%\target\client-exec.jar %ROOT%\"
    call :color "%CL_INFO%" "The executable file is located here: %ROOT%\client-exec.jar"

    goto :eof

:test
    call :color "%CL_HEADER%" "Starting tests..."

    call :eval_echo "%MVNW% clean test"

    goto :eof

:run
    call :color "%CL_HEADER%" "Running client..."

    rem run jar
    rem call :eval_echo "%JAVA% -Dfile.encoding=UTF-8 -jar %ROOT%\client-exec.jar `%GAME_TO_RUN%` `%BOARD_URL%`"

    rem build & run (without jar)
    call :eval_echo "%MVNW% clean compile exec:java -Dfile.encoding=UTF-8 -Dexec.mainClass=com.codenjoy.dojo.JavaRunner -Dexec.args=`%GAME_TO_RUN% %BOARD_URL%`"

    goto :eof