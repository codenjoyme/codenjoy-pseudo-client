call 0-settings.bat

echo off
call lib :color Starting pseudo client...
echo on

call %JAVA% -Dfile.encoding=UTF-8 -jar %ROOT%/app.jar "%GAME_TO_RUN%" "%BOARD_URL%" "%PSEUDO_RULES%"

rem another way to run
rem call %MVNW% exec:java -Dfile.encoding=UTF-8 -D"exec.mainClass"="com.codenjoy.dojo.PseudoRunner" -D"exec.args"="%GAME_TO_RUN% %BOARD_URL% %PSEUDO_RULES%"

call lib :ask