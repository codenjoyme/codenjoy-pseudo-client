#!/usr/bin/env bash

. 0-settings.sh

color $COLOR1 "Installing java..."
echo

eval_echo "[[ \"$SKIP_JDK_INSTALL\" == \"true\" ]] && skip"
eval_echo "[[ \"$INSTALL_LOCALLY\" == \"false\" ]] && skip"
eval_echo "[[ \"$INSTALL_LOCALLY\" == \"\" ]] && skip"

eval_echo "install 'jdk' '$ARCH_URL' '$ARCH_FOLDER'"
eval_echo_color_output "$MVNW -v"

ask

skip() {
    color $COLOR3 "Installation skipped"
    color $COLOR3 "INSTALL_LOCALLY=$INSTALL_LOCALLY"
    color $COLOR3 "SKIP_JDK_INSTALL=$SKIP_JDK_INSTALL"
    ask
    exit
}