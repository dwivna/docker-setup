provider "aws" {
  access_key                  = "test"
  secret_key                  = "test"
  region                      = "us-east-1"
  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_requesting_account_id  = true
  skip_metadata_api_check     = true
  endpoints {
    s3       = "http://localstack:4566"                      # Updated to match LocalStack URL
    dynamodb = "http://localstack:4566"
  }
}

resource "aws_s3_bucket" "dwivna-info" {
  bucket = "dwivna-info-bucket"
}

resource "aws_dynamodb_table" "dwivna-info-ddb-table" {
  name           = "info_table"   # DynamoDB table name
  hash_key       = "id"              # Partition key (primary key)
  range_key      = "timestamp"       # Sort key (optional)
  billing_mode   = "PAY_PER_REQUEST" # On-demand capacity mode (use for LocalStack)

  attribute {
    name = "id"                       # Attribute for partition key
    type = "S"                        # S for String type
  }

  attribute {
    name = "timestamp"                 # Attribute for sort key
    type = "N"                         # N for Number type
  }

  tags = {
    Name        = "LocalStack Information Table"
    Environment = "Development"
  }
}