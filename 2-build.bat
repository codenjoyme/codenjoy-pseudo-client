call 0-settings.bat

echo off
call lib :color Building pseudo client...
echo on

call lib :print_color %MVNW% -v

rem install jar to maven local repo
call %MVNW% clean install -DskipTests=%SKIP_TESTS%

rem create executable jar
call %MVNW% compile assembly:single -Pjar-with-dependencies -DskipTests=%SKIP_TESTS%
echo F|xcopy /y %ROOT%\target\client-pseudo-exec.jar %ROOT%\app.jar

call lib :ask