call 0-settings.bat

echo off
call lib.bat :color Installing java...
echo on

if "%SKIP_JDK_INSTALL%"=="true" ( goto :skip )
if "%INSTALL_LOCALLY%"=="false" ( goto :skip )
if "%INSTALL_LOCALLY%"=="" ( goto :skip )

call lib.bat :install jdk
call lib.bat :print_color %MVNW% -v

call lib.bat :ask

goto :eof

:skip
	echo off
	call lib.bat :color Installation skipped
	call lib.bat :color INSTALL_LOCALLY=%INSTALL_LOCALLY%
	call lib.bat :color SKIP_JDK_INSTALL=%SKIP_JDK_INSTALL%
	echo on
	call lib.bat :ask
    goto :eof