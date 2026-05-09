#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DEST="$SCRIPT_DIR/bin"
TEST="$SCRIPT_DIR/resources/tests"

mkdir -p "$DEST"

javac -d "$DEST" $(find "$SCRIPT_DIR/JsonParser/src" -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation Failed"
    exit 1
fi

java -cp "$DEST" src.JsonParser "$TEST"