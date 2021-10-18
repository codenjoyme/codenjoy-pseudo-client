set GAME_TO_RUN=mollymage
set PSEUDO_HERO_ELEMENTS=HERO,POTION_HERO,DEAD_HERO
set BOARD_URL=http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000

set ROOT=%CD%

set SKIP_TESTS=true

set CODE_PAGE=65001
chcp %CODE_PAGE%

set MVNW=mvnw
set MAVEN_OPTS=-Dmaven.repo.local=%ROOT%\..\.m2\repository

set PSEUDO_CLIENT_HOME=%ROOT%
set PSEUDO_RULES=%PSEUDO_CLIENT_HOME%\rules