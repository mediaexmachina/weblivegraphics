#!/bin/bash
BASE_URL="http://localhost:8080";
BASE_URL_API="$BASE_URL/v1/weblivegraphics";

# Create keyer
curl -X POST "$BASE_URL_API/layers/keyer?label=toptitle"

# Active it on program
curl -X PUT "$BASE_URL_API/layers/keyer/pgm?label=toptitle&active=true"

# Create item
curl -X POST "$BASE_URL_API/layers/keyer/item?keyerLabel=toptitle&itemLabel=TopLiveTitle&typeName=mainlivetitle&active=true"

# Put setup for created item
curl -d '{"text":"My Live Title"}' -X PUT -H "Content-Type: application/json" "$BASE_URL_API/layers/keyer/item/setup?keyerLabel=toptitle&itemLabel=TopLiveTitle"
