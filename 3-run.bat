call 0-settings.bat

echo off
call lib :color Starting pseudo client...
echo on

call %JAVA% -Dfile.encoding=UTF-8 -jar %ROOT%/app.jar "%GAME_TO_RUN%" "%BOARD_URL%" "%PSEUDO_RULES%"

call lib :ask