#!/usr/bin/env bash

PORT=${1:-8888}
INDEX_NAME=${2:-index_english_0}
PROPAGATE=${3:-true}
INCREMENTAL=${4:-true}
curl -v -H "Authorization: Basic $(echo -n 'test_user:p4ssw0rd' | base64)" \
  -H "Content-Type: application/json" -X POST "http://localhost:${PORT}/${INDEX_NAME}/decisiontable_analyzer?propagate=${PROPAGATE}&incremental=${INCREMENTAL}"

