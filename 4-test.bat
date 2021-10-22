call 0-settings.bat

echo off
call lib :color Starting pseudo tests...
echo on

call %MVNW% clean test

call lib :ask