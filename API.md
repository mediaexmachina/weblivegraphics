# REST API

## Summary <a name="top"></a>

* [**GET** `/v1/weblivegraphics/front/refresh-layers`](#goto3644120585)
* [**GET** `/v1/weblivegraphics/layers/`](#goto3530439175)
* [**PUT** `/v1/weblivegraphics/layers/bypass/off`](#goto1777561426)
* [**PUT** `/v1/weblivegraphics/layers/bypass/on`](#goto4095497441)
* [**PUT** `/v1/weblivegraphics/layers/dsk/label?label=<String>`](#goto1100775687)
* [**PUT** `/v1/weblivegraphics/layers/dsk/pgm?active=<boolean>`](#goto3949984969)
* [**PUT** `/v1/weblivegraphics/layers/dsk/pvw?active=<boolean>`](#goto334093836)
* [**GET** `/v1/weblivegraphics/layers/dsk`](#goto3825314938)
* [**PUT** `/v1/weblivegraphics/layers/keyer/item/active?keyerLabel=<String>&itemLabel=<String>&active=<boolean>`](#goto723987679)
* [**POST** `/v1/weblivegraphics/layers/keyer/item/move?keyerLabel=<String>&itemLabel=<String>&newKeyer=<String>`](#goto111475588)
* [**PUT** `/v1/weblivegraphics/layers/keyer/item/setup?keyerLabel=<String>&itemLabel=<String>`](#goto3019336356)
* [**POST** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>&typeName=<String>&active=<boolean>`](#goto2439544347)
* [**DELETE** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>`](#goto1772645336)
* [**GET** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>`](#goto4004480069)
* [**POST** `/v1/weblivegraphics/layers/keyer/move?label=<String>&newPos=<int>`](#goto996658272)
* [**PUT** `/v1/weblivegraphics/layers/keyer/pgm?label=<String>&active=<boolean>`](#goto3661132383)
* [**PUT** `/v1/weblivegraphics/layers/keyer/pvw?label=<String>&active=<boolean>`](#goto63412551)
* [**DELETE** `/v1/weblivegraphics/layers/keyer?label=<String>`](#goto3057591294)
* [**GET** `/v1/weblivegraphics/layers/keyer?label=<String>`](#goto4210837688)
* [**POST** `/v1/weblivegraphics/layers/keyer?label=<String>`](#goto2020511672)

## <a name="goto3644120585"></a> **GET** `/v1/weblivegraphics/front/refresh-layers`

getLayers

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [FrontRestController :: getLayers](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/FrontRestController.java#38)

## <a name="goto3530439175"></a> **GET** `/v1/weblivegraphics/layers/`

getLayers

```javascript
Response: "application/json" {
    downStreamKeyer: {
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    },
    fullBypass: boolean,
    keyers: [{
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    }, ...]
}
```

[Go to the top](#top) &bull; [LayersAPI :: getLayers](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#53)

## <a name="goto1777561426"></a> **PUT** `/v1/weblivegraphics/layers/bypass/off`

switchBypassOff

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchBypassOff](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#64)

## <a name="goto4095497441"></a> **PUT** `/v1/weblivegraphics/layers/bypass/on`

switchBypassOn

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchBypassOn](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#58)

## <a name="goto1100775687"></a> **PUT** `/v1/weblivegraphics/layers/dsk/label?label=<String>`

setDSKKeyerLabel

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: setDSKKeyerLabel](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#90)

## <a name="goto3949984969"></a> **PUT** `/v1/weblivegraphics/layers/dsk/pgm?active=<boolean>`

switchDSKKeyerPgm

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchDSKKeyerPgm](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#76)

## <a name="goto334093836"></a> **PUT** `/v1/weblivegraphics/layers/dsk/pvw?active=<boolean>`

switchDSKKeyerPvw

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchDSKKeyerPvw](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#83)

## <a name="goto3825314938"></a> **GET** `/v1/weblivegraphics/layers/dsk`

getDSK

```javascript
Response: "application/json" {
    activePreview: boolean,
    activeProgram: boolean,
    id: UUID,
    items: [{
        active: boolean,
        id: UUID,
        label: String,
        setup: {

        },
        typeName: String
    }, ...],
    label: String
}
```

[Go to the top](#top) &bull; [LayersAPI :: getDSK](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#70)

## <a name="goto723987679"></a> **PUT** `/v1/weblivegraphics/layers/keyer/item/active?keyerLabel=<String>&itemLabel=<String>&active=<boolean>`

switchItemActive

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchItemActive](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#181)

## <a name="goto111475588"></a> **POST** `/v1/weblivegraphics/layers/keyer/item/move?keyerLabel=<String>&itemLabel=<String>&newKeyer=<String>`

moveItemInKeyer

```javascript
Response: "application/json" {
    downStreamKeyer: {
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    },
    fullBypass: boolean,
    keyers: [{
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    }, ...]
}
```

[Go to the top](#top) &bull; [LayersAPI :: moveItemInKeyer](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#199)

## <a name="goto3019336356"></a> **PUT** `/v1/weblivegraphics/layers/keyer/item/setup?keyerLabel=<String>&itemLabel=<String>`

setItemSetup

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: setItemSetup](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#190)

## <a name="goto2439544347"></a> **POST** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>&typeName=<String>&active=<boolean>`

createItem

```javascript
Response: "application/json" {
    active: boolean,
    id: UUID,
    label: String,
    setup: {

    },
    typeName: String
}
```

[Go to the top](#top) &bull; [LayersAPI :: createItem](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#163)

## <a name="goto1772645336"></a> **DELETE** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>`

deleteItem

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: deleteItem](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#173)

## <a name="goto4004480069"></a> **GET** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>`

getItem

```javascript
Response: "application/json" {
    active: boolean,
    id: UUID,
    label: String,
    setup: {

    },
    typeName: String
}
```

[Go to the top](#top) &bull; [LayersAPI :: getItem](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#156)

## <a name="goto996658272"></a> **POST** `/v1/weblivegraphics/layers/keyer/move?label=<String>&newPos=<int>`

moveKeyer

```javascript
Response: "application/json" {
    downStreamKeyer: {
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    },
    fullBypass: boolean,
    keyers: [{
        activePreview: boolean,
        activeProgram: boolean,
        id: UUID,
        items: [{
            active: boolean,
            id: UUID,
            label: String,
            setup: {

            },
            typeName: String
        }, ...],
        label: String
    }, ...]
}
```

[Go to the top](#top) &bull; [LayersAPI :: moveKeyer](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#209)

## <a name="goto3661132383"></a> **PUT** `/v1/weblivegraphics/layers/keyer/pgm?label=<String>&active=<boolean>`

switchKeyerPgm

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchKeyerPgm](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#125)

## <a name="goto63412551"></a> **PUT** `/v1/weblivegraphics/layers/keyer/pvw?label=<String>&active=<boolean>`

switchKeyerPvw

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchKeyerPvw](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#133)

## <a name="goto3057591294"></a> **DELETE** `/v1/weblivegraphics/layers/keyer?label=<String>`

deleteKeyer

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: deleteKeyer](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#118)

## <a name="goto4210837688"></a> **GET** `/v1/weblivegraphics/layers/keyer?label=<String>`

getKeyer

```javascript
Response: "application/json" {
    activePreview: boolean,
    activeProgram: boolean,
    id: UUID,
    items: [{
        active: boolean,
        id: UUID,
        label: String,
        setup: {

        },
        typeName: String
    }, ...],
    label: String
}
```

[Go to the top](#top) &bull; [LayersAPI :: getKeyer](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#102)

## <a name="goto2020511672"></a> **POST** `/v1/weblivegraphics/layers/keyer?label=<String>`

createKeyer

```javascript
Response: "application/json" {
    activePreview: boolean,
    activeProgram: boolean,
    id: UUID,
    items: [{
        active: boolean,
        id: UUID,
        label: String,
        setup: {

        },
        typeName: String
    }, ...],
    label: String
}
```

[Go to the top](#top) &bull; [LayersAPI :: createKeyer](/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#112)

