#!/usr/bin/env bash

. 0-settings.sh

color $COLOR1 "Building pseudo client..."
echo

eval_echo_color_output "$MVNW -v"

# install jar to maven local repo
eval_echo "$MVNW clean install -DskipTests=$SKIP_TESTS"

# create executable jar
eval_echo "$MVNW compile assembly:single -Pjar-with-dependencies -DskipTests=$SKIP_TESTS"
eval_echo "mv -f $ROOT/target/client-pseudo-exec.jar $ROOT/app.jar"
ls -la

ask
