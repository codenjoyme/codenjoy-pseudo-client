#!/usr/bin/env bash

. lib.sh

color $COLOR1 "Setup variables..."
echo

eval_echo "[[ \"$GAME_TO_RUN\" == \"\" ]]  && GAME_TO_RUN=mollymage"
eval_echo "[[ \"$BOARD_URL\" == \"\" ]]    && BOARD_URLhttp://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000"
eval_echo "[[ \"$PSEUDO_RULES\" == \"\" ]] && PSEUDO_RULES=./rules/$GAME_TO_RUN"

eval_echo "ROOT=$PWD"

eval_echo "PSEUDO_CLIENT_HOME=$ROOT"