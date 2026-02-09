#!/usr/bin/env bash
# wait-for-it.sh - Wait for MySQL/Mongo to be ready
set -e

host="$1"
shift
cmd="$@"

until nc -z "$host" 3306; do
  echo "Waiting for $host:3306..."
  sleep 2
done

exec $cmd
