#!/usr/bin/env bash
set -euo pipefail

echo "[localstack-init] Creating RDS instance metadata..."
awslocal rds create-db-instance \
  --db-instance-identifier cep-rds-postgres \
  --db-instance-class db.t3.micro \
  --engine postgres \
  --master-username cepuser \
  --master-user-password ceppass123 \
  --allocated-storage 20 \
  --db-name cepdb \
  --backup-retention-period 0 \
  --publicly-accessible \
  --no-multi-az || true

echo "[localstack-init] Waiting RDS status..."
awslocal rds wait db-instance-available --db-instance-identifier cep-rds-postgres || true

echo "[localstack-init] RDS describe output:"
awslocal rds describe-db-instances --db-instance-identifier cep-rds-postgres || true
