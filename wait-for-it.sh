#!/usr/bin/env bash
# wait-for-it.sh - Wait for MySQL/Mongo to be ready
set -e

host="$1"
port="$2"
shift 2
cmd="$@"

until nc -z "$host" "$port"; do
  echo "Waiting for $host:$port..."
  sleep 2
done

exec $cmd
