#!/usr/bin/env bash

. 0-settings.sh

color $COLOR1 "Starting pseudo client..."
echo

# run jar
eval_echo "$JAVA -Dfile.encoding=UTF-8 -jar $ROOT/app.jar '$GAME_TO_RUN' '$BOARD_URL'"

# build & run (without jar)
# eval_echo "$MVNW clean compile exec:java -Dfile.encoding=UTF-8 -D'exec.mainClass'='com.codenjoy.dojo.PseudoRunner' -D'exec.args'='$GAME_TO_RUN $BOARD_URL'"

ask
