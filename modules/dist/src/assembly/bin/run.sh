#!/usr/bin/env sh
set -eu

APP_HOME="$(cd "$(dirname "$0")/.." && pwd)"
APP_JAR="$(ls "$APP_HOME"/app-cli-*.jar | head -n 1)"

exec java -cp "$APP_JAR:$APP_HOME/lib/*" io.github.josemanuel.maven.app.CliApp "$@"