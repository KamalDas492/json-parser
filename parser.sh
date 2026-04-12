#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC="$SCRIPT_DIR/src/JsonParser.java"
DEST="$SCRIPT_DIR/bin"
TEST="$SCRIPT_DIR/resources/tests"

if [ ! -f "$DEST/JsonParser.class" ] || [ "$SRC" -nt "$DEST/JsonParser.class" ]; then
	mkdir -p "$DEST"
	javac -d "$DEST" "$SRC"
	if [ $? -ne 0 ]; then
		echo "Compliation Failed"
		exit 1
	fi
fi

java -cp "$DEST" src.JsonParser "$TEST"


