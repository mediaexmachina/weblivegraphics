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

# Create clock
curl -X POST "$BASE_URL_API/layers/keyer/item?keyerLabel=toptitle&itemLabel=TopClock&typeName=clock&active=true"

# Create Dynamic Summary
curl -X POST "$BASE_URL_API/dynsummary?summary=ExampleSum&active=true"

# Put chapters for this Summary
curl -d '{"chapters":["Chapter 1", "Chapter 2"]}' -X PUT -H "Content-Type: application/json" "$BASE_URL_API/dynsummary/chapters?summary=ExampleSum"

# Put Dynamic Summary on "toptitle" keyer
curl -X POST "$BASE_URL_API/layers/keyer/item?keyerLabel=toptitle&itemLabel=DynSum&typeName=dynsummary&active=true"

# curl -X PUT http://localhost:8080/v1/weblivegraphics/dynsummary/active/hide
# curl -X PUT http://localhost:8080/v1/weblivegraphics/dynsummary/active/show
