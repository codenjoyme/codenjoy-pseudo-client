call 0-settings.bat

echo off
call lib.bat :color Building pseudo client...
echo on

call lib.bat :print_color %MVNW% -v
call %MVNW% clean install -DskipTests=%SKIP_TESTS%

call lib.bat :ask