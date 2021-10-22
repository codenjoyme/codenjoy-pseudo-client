call 0-settings.bat

echo off
call lib.bat :color Starting pseudo client...
echo on

call %MVNW% exec:java -Dfile.encoding=UTF-8 -D"exec.mainClass"="com.codenjoy.dojo.Runner" -D"exec.args"="%GAME_TO_RUN% %BOARD_URL% %PSEUDO_RULES%"

call lib.bat :ask