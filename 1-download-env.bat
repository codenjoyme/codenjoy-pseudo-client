call 0-settings.bat

echo off
echo        [44;93m+-------------------------------------+[0m
echo        [44;93m!           Installing JDK            ![0m
echo        [44;93m+-------------------------------------+[0m
echo on

if "%SKIP_JDK_INSTALL%"=="true" ( goto :skip )
if "%INSTALL_LOCALLY%"=="false" ( goto :skip )
if "%INSTALL_LOCALLY%"=="" ( goto :skip )

cd %ROOT%
IF EXIST %TOOLS%\jdk.zip (
    del /Q %TOOLS%\jdk.zip
)
powershell -command "& { set-executionpolicy remotesigned -s currentuser; [System.Net.ServicePointManager]::SecurityProtocol = 3072 -bor 768 -bor 192 -bor 48; $client=New-Object System.Net.WebClient; $client.Headers.Add([System.Net.HttpRequestHeader]::Cookie, 'oraclelicense=accept-securebackup-cookie'); $client.DownloadFile('%ARCH_JDK%','%TOOLS%\jdk.zip') }"
rd /S /Q %TOOLS%\..\.jdk
%ARCH% x -y -o%TOOLS%\.. %TOOLS%\jdk.zip
rename %TOOLS%\..\%ARCH_JDK_FOLDER% .jdk
cd %ROOT%

call :ask

goto :eof

:skip
	echo off
	echo        [44;93m  Installation skipped:       [0m
	echo        [44;93m      INSTALL_LOCALLY=%INSTALL_LOCALLY%     [0m
	echo        [44;93m      SKIP_JDK_INSTALL=%SKIP_JDK_INSTALL%        [0m
	echo on
	goto :ask
goto :eof

:ask
    echo off
    echo        [44;93m+---------------------------------+[0m
    echo        [44;93m!    Press any key to continue    ![0m
    echo        [44;93m+---------------------------------+[0m
    echo on
    pause >nul
goto :eof