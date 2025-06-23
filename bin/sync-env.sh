#!/usr/bin/env bash
set -euo pipefail

# 1) Prompt for the passphrase if ENV_PASSPHRASE isn’t already set
if [[ -z "${ENV_PASSPHRASE-}" ]]; then
  read -rsp "Enter shared ENV_PASSPHRASE: " PASSPHRASE
  echo
else
  PASSPHRASE="$ENV_PASSPHRASE"
fi

# 2) Identify the latest run of our export workflow
WORKFLOW_FILE="export-secrets.yml"
RUN_ID=$(gh run list --workflow="$WORKFLOW_FILE" --limit 1 --json databaseId --jq '.[0].databaseId')

if [[ -z "$RUN_ID" ]]; then
  echo "❌ No workflow run found for $WORKFLOW_FILE"
  exit 1
fi

echo "📥 Downloading artifact from run ID $RUN_ID..."

# 3) Prepare a clean download directory
ARTIFACT_DIR="encrypted-secrets"
rm -rf "$ARTIFACT_DIR"
mkdir -p "$ARTIFACT_DIR"

# 4) Download the encrypted .env.gpg
gh run download "$RUN_ID" --name encrypted-secrets --dir "$ARTIFACT_DIR"

# 5) Decrypt into .env
echo "🔓 Decrypting into .env..."
gpg --quiet --batch \
    --yes \
    --pinentry-mode loopback \
    --passphrase "$PASSPHRASE" \
    --output .env \
    --decrypt "$ARTIFACT_DIR/secrets.env.gpg"

echo "✅ .env synced and ready"
