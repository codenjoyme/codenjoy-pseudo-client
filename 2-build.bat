call 0-settings.bat

echo off
call lib :color Building pseudo client...
echo on

call lib :print_color %MVNW% -v
call %MVNW% clean install -DskipTests=%SKIP_TESTS%

call lib :ask