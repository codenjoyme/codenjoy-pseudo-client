call 0-settings.bat

echo off
call lib :color Installing java...
echo on

if "%SKIP_JDK_INSTALL%"=="true" ( goto :skip )
if "%INSTALL_LOCALLY%"=="false" ( goto :skip )
if "%INSTALL_LOCALLY%"=="" ( goto :skip )

call lib :install jdk %ARCH_URL% %ARCH_FOLDER%
call lib :print_color %MVNW% -v

call lib :ask

goto :eof

:skip
	echo off
	call lib :color Installation skipped
	call lib :color INSTALL_LOCALLY=%INSTALL_LOCALLY%
	call lib :color SKIP_JDK_INSTALL=%SKIP_JDK_INSTALL%
	echo on
	call lib :ask
    goto :eof