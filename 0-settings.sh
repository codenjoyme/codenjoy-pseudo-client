#!/usr/bin/env bash

. lib.sh

color $COLOR1 "Setup variables..."
echo

eval_echo "[[ \"$GAME_TO_RUN\" == \"\" ]]  && GAME_TO_RUN=mollymage"
eval_echo "[[ \"$BOARD_URL\" == \"\" ]]    && BOARD_URL=http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000"
eval_echo "[[ \"$PSEUDO_RULES\" == \"\" ]] && PSEUDO_RULES=.\rules"

eval_echo "ROOT=$PWD"

eval_echo "[[ \"$SKIP_TESTS\" == \"\" ]]   && SKIP_TESTS=true"

eval_echo "TOOLS=$ROOT/.tools"
eval_echo "ARCH=tar"

# Set to true if you want to ignore jdk and maven installation on the system
eval_echo "[[ \"$INSTALL_LOCALLY\" == \"\" ]]   && INSTALL_LOCALLY=true"

eval_echo "[[ \"$INSTALL_LOCALLY\" == "true" ]] && export JAVA_HOME="
eval_echo "[[ \"$INSTALL_LOCALLY\" == "true" ]] && export MAVEN_HOME="

eval_echo "[[ \"$JAVA_HOME\" == \"\" ]]  && export JAVA_HOME=$ROOT/.jdk"
eval_echo "[[ \"$MAVEN_HOME\" == \"\" ]] && NO_MAVEN=true"
eval_echo "[[ \"$NO_MAVEN\" == "true" ]] && export MAVEN_HOME=$ROOT/.mvn"
eval_echo "[[ \"$NO_MAVEN\" == "true" ]] && export MAVEN_USER_HOME=$ROOT/.mvn"
eval_echo "[[ \"$NO_MAVEN\" == "true" ]] && export MAVEN_OPTS=-Dmaven.repo.local=$MAVEN_HOME/repository"

eval_echo "MVNW=$ROOT/mvnw"
eval_echo "chmod +x $MVNW"
eval_echo "export MVNW_VERBOSE=false"
eval_echo "JAVA=$JAVA_HOME/bin/java"
eval_echo "export PATH=\"$JAVA_HOME/bin:$PATH\""

color $COLOR4 "JAVA_HOME=$JAVA_HOME"
color $COLOR4 "MAVEN_HOME=$MAVEN_HOME"
color $COLOR4 "MAVEN_OPTS=$MAVEN_OPTS"
color $COLOR4 "MAVEN_USER_HOME=$MAVEN_USER_HOME"
color $COLOR4 "MVNW_VERBOSE=$MVNW_VERBOSE"
echo

eval_echo "ARCH_URL=https://builds.openlogic.com/downloadJDK/openlogic-openjdk/11.0.11%2B9/openlogic-openjdk-11.0.11%2B9-linux-x64.tar.gz"
eval_echo "ARCH_FOLDER=openlogic-openjdk-11.0.11+9-linux-x64"

eval_echo "PSEUDO_CLIENT_HOME=$ROOT"