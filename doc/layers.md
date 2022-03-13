# Create / setup new layers

## Z-Index reference

| Section name     | Z-Index |
| ---------------- | ------- |
| DSK              | 10000   |
| Keyers           | 100...  |
| Image background | 10      |

## Live title

Kind: static text.

Type name: `mainlivetitle`.

Expected setup:

```json
{"text":"My Live Title"}
```

<details>
<summary>Create calls example</summary>

```bash
# Create item
curl -X POST "http://localhost:8080/v1/weblivegraphics/layers/keyer/item?keyerLabel=toptitle&itemLabel=TopLiveTitle&typeName=mainlivetitle&active=true"

# Put setup for created item
curl -d '{"text":"My Live Title"}' -X PUT -H "Content-Type: application/json" "http://localhost:8080/v1/
weblivegraphics/layers/keyer/item/setup?keyerLabel=toptitle&itemLabel=TopLiveTitle"
```

</details>

## Clock

Kind: static text (static from back ; updated by front).

Type name: `clock`.

Don't need setup.

<details>
<summary>Create call example</summary>

```bash
curl -X POST "http://localhost:8080/v1/weblivegraphics/layers/keyer/item?keyerLabel=toptitle&itemLabel=TopClock&typeName=clock&active=true"
```

</details>
