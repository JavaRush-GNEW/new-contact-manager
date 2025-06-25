#!/usr/bin/env bash
set -euo pipefail

# 1. Find the most recent export run
WORKFLOW="Export & Encrypt Secrets"
RUN_ID=$(gh run list --workflow="export-secrets.yml" --limit 1 --json databaseId --jq '.[0].databaseId')

# 2. Download the artifact
gh run download "$RUN_ID" --name encrypted-secrets --dir .

# 3. Decrypt to .env
gpg --quiet --batch \
    --yes \
    --pinentry-mode loopback \
    --passphrase "${ENV_PASSPHRASE:-}" \
    --output .env \
    --decrypt secrets.env.gpg

echo "✅ .env synced and ready"
