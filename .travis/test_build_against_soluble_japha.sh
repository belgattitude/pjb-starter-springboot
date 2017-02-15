#!/usr/bin/env bash
#
# Travis specific post-test script to run the test suite provided
# in the latest release of https://github.com/belgattitude/soluble-japha client.
#
# Very hacky
#
# usage:
#   > ./test_build_against_soluble_japha.sh
#
# requirements:
#   - php >= 5.6, php 7+
#   - git
#   - composer
#   - java
#   - linux
#
# @author Vanvelthem Sébastien
#

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$SCRIPT_DIR/.."
JAPHA_DIR="$SCRIPT_DIR/soluble-japha"
STANDALONE_JAR="$PROJECT_DIR/build/libs/JavaBridgeStandalone.jar"
VAR_DIR="$SCRIPT_DIR/var" # Where to store log and pid files
SERVER_PIDFILE="$VAR_DIR/standalone.pid"

install_soluble_japha() {

    echo "[*] Installing latest release of soluble-japha";

    # 1. Clone the soluble-japha project (if not already exists)
    if [ ! -d $JAPHA_DIR ]; then
        git clone https://github.com/belgattitude/soluble-japha.git $JAPHA_DIR
    fi

    # 2. Clone the soluble-japha project
    cd $JAPHA_DIR

    # 3. Checkout latest release
    git fetch --tags
    latestTag=$(git describe --tags `git rev-list --tags --max-count=1`)
    git checkout $latestTag

    # 4. Run composer install
    composer install

    # 5. Restore path
    cd $PROJECT_DIR
}

buildStandalone() {

    echo "[*] Building standalone bridge server";

    # clean, build the jar, repackage for standalone.
    gradle clean build jar bootRepackage

    if [ ! -f $STANDALONE_JAR ]; then
        echo "[ERROR] Standlone was not built or jar missing ($STANDALONE_JAR)"
        exit -1
    fi
}

runStandaloneInBackground() {

    OUTPUT_LOG="$VAR_DIR/output.log"

    if [ -f "$OUTPUT_LOG" ]; then
        rm "$OUTPUT_LOG";
    fi;

    echo "[*] Will run standalone in background (very hacky way)";

    cd $JAPHA_DIR
    CMD="java -jar $STANDALONE_JAR 2>&1 > $OUTPUT_LOG & echo \$! > $SERVER_PIDFILE"
    echo "    - with command: $CMD"
    eval $CMD
    echo "    - Should be running with pid: "  `cat $VAR_DIR/standalone.pid`

    echo "Poor's man waiting for the server to start"
    echo "Please wait..."
    count=0
    success=1
    pattern="Started Application in"
    if [ -f $OUTPUT_LOG ]; then
        until cat $OUTPUT_LOG | grep "$pattern"
        do
            count=$(($count + 1))
            echo "...$count..."
            if [ $count -gt 15 ]; then
                echo "TIMEOUT EXCEEDED"
                success=0
                break;
            fi
          sleep 1
        done
    else
        echo "OUTPUT file does not exists"
        success=0
    fi;

    echo "Here's the server output:"
    cat "$OUTPUT_LOG"

    if [ -z success ]; then
        echo "[ERROR] Server not started"
        return -1
    else
        echo "[*] Server should have been started ;)"
    fi

    # restore path
    cd $PROJECT_DIR

}

runPHPUnit()  {

    cd $JAPHA_DIR
    echo "[*] Running phpunit"
    cp ../phpunit.travis.xml .
    ./vendor/bin/phpunit -c ./phpunit.travis.xml
}

# Here's the steps

install_soluble_japha;
runStandaloneInBackground;
runPHPUnit;
# Kill standalone
kill `cat $VAR_DIR/standalone.pid`

