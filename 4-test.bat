call 0-settings.bat

echo off
echo        [44;93m+-------------------------------------------------+[0m
echo        [44;93m!       Now we are starting pseudo tests...       ![0m
echo        [44;93m+-------------------------------------------------+[0m
echo on

call %MVNW% clean test

call :ask

goto :eof

:ask
    echo off
    echo        [44;93m+---------------------------------+[0m
    echo        [44;93m!    Press any key to continue    ![0m
    echo        [44;93m+---------------------------------+[0m
    echo on
    pause >nul
goto :eof
