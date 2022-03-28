# REST API

## Summary <a name="top"></a>

* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/first?summary=<String>`](#goto3718431865)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/hide`](#goto4152393848)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/last?summary=<String>`](#goto4033681551)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/next?summary=<String>`](#goto4038289799)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/previous?summary=<String>`](#goto3967551827)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter/show`](#goto1628934738)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/chapter?summary=<String>&chapter=<int>`](#goto1179943059)
* [**GET** `/v1/weblivegraphics/dynsummary/active/chapter?summary=<String>`](#goto1372768250)
* [**GET** `/v1/weblivegraphics/dynsummary/active/chapters`](#goto2809224877)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/hide`](#goto1646100089)
* [**PUT** `/v1/weblivegraphics/dynsummary/active/show`](#goto4102712403)
* [**PUT** `/v1/weblivegraphics/dynsummary/active?summary=<String>`](#goto1813480663)
* [**GET** `/v1/weblivegraphics/dynsummary/active`](#goto4152038016)
* [**GET** `/v1/weblivegraphics/dynsummary/chapters?summary=<String>`](#goto989120706)
* [**PUT** `/v1/weblivegraphics/dynsummary/chapters?summary=<String>`](#goto2856483767)
* [**POST** `/v1/weblivegraphics/dynsummary?summary=<String>&active=<boolean>`](#goto2566279466)
* [**DELETE** `/v1/weblivegraphics/dynsummary?summary=<String>`](#goto2510365236)
* [**GET** `/v1/weblivegraphics/dynsummary`](#goto2367657518)
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

## <a name="goto3718431865"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/first?summary=<String>`

setActiveFirstChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActiveFirstChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#146)

## <a name="goto4152393848"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/hide`

setHideActiveChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setHideActiveChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#164)

## <a name="goto4033681551"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/last?summary=<String>`

setActiveLastChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActiveLastChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#152)

## <a name="goto4038289799"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/next?summary=<String>`

setActiveNextChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActiveNextChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#134)

## <a name="goto3967551827"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/previous?summary=<String>`

setActivePrevChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActivePrevChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#140)

## <a name="goto1628934738"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter/show`

setShowActiveChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setShowActiveChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#158)

## <a name="goto1179943059"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/chapter?summary=<String>&chapter=<int>`

setActiveChapter

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActiveChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#127)

## <a name="goto1372768250"></a> **GET** `/v1/weblivegraphics/dynsummary/active/chapter?summary=<String>`

getActiveChapter

```javascript
Response: "application/json" {
    chapter: String,
    summary: String
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: getActiveChapter](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#121)

## <a name="goto2809224877"></a> **GET** `/v1/weblivegraphics/dynsummary/active/chapters`

getActiveChapters

```javascript
Response: "application/json" {
    chapters: [String, ...],
    summary: String
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: getActiveChapters](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#114)

## <a name="goto1646100089"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/hide`

setHideSummary

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setHideSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#176)

## <a name="goto4102712403"></a> **PUT** `/v1/weblivegraphics/dynsummary/active/show`

setShowSummary

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setShowSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#170)

## <a name="goto1813480663"></a> **PUT** `/v1/weblivegraphics/dynsummary/active?summary=<String>`

setActiveSummary

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setActiveSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#102)

## <a name="goto4152038016"></a> **GET** `/v1/weblivegraphics/dynsummary/active`

getActiveSummary

```javascript
Response: "application/json" {
    summary: String
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: getActiveSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#108)

## <a name="goto989120706"></a> **GET** `/v1/weblivegraphics/dynsummary/chapters?summary=<String>`

getChapters

```javascript
Response: "application/json" {
    chapters: [String, ...],
    summary: String
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: getChapters](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#89)

## <a name="goto2856483767"></a> **PUT** `/v1/weblivegraphics/dynsummary/chapters?summary=<String>`

setChapters

```javascript
Request body data: {
    chapters: [String, ...]
}
```

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: setChapters](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#95)

## <a name="goto2566279466"></a> **POST** `/v1/weblivegraphics/dynsummary?summary=<String>&active=<boolean>`

createSummary

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: createSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#73)

## <a name="goto2510365236"></a> **DELETE** `/v1/weblivegraphics/dynsummary?summary=<String>`

deleteSummary

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: deleteSummary](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#83)

## <a name="goto2367657518"></a> **GET** `/v1/weblivegraphics/dynsummary`

getSummaries

```javascript
Response: "application/json" {
    summaries: [{
        chapters: [String, ...],
        name: String,
        selected: boolean
    }, ...]
}
```

[Go to the top](#top) &bull; [DynamicalSummaryController :: getSummaries](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/DynamicalSummaryController.java#59)

## <a name="goto3644120585"></a> **GET** `/v1/weblivegraphics/front/refresh-layers`

getLayers

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [FrontRestController :: getLayers](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/FrontRestController.java#41)

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

[Go to the top](#top) &bull; [LayersAPI :: getLayers](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#56)

## <a name="goto1777561426"></a> **PUT** `/v1/weblivegraphics/layers/bypass/off`

switchBypassOff

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchBypassOff](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#67)

## <a name="goto4095497441"></a> **PUT** `/v1/weblivegraphics/layers/bypass/on`

switchBypassOn

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchBypassOn](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#61)

## <a name="goto1100775687"></a> **PUT** `/v1/weblivegraphics/layers/dsk/label?label=<String>`

setDSKKeyerLabel

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: setDSKKeyerLabel](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#93)

## <a name="goto3949984969"></a> **PUT** `/v1/weblivegraphics/layers/dsk/pgm?active=<boolean>`

switchDSKKeyerPgm

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchDSKKeyerPgm](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#79)

## <a name="goto334093836"></a> **PUT** `/v1/weblivegraphics/layers/dsk/pvw?active=<boolean>`

switchDSKKeyerPvw

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchDSKKeyerPvw](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#86)

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

[Go to the top](#top) &bull; [LayersAPI :: getDSK](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#73)

## <a name="goto723987679"></a> **PUT** `/v1/weblivegraphics/layers/keyer/item/active?keyerLabel=<String>&itemLabel=<String>&active=<boolean>`

switchItemActive

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchItemActive](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#184)

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

[Go to the top](#top) &bull; [LayersAPI :: moveItemInKeyer](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#202)

## <a name="goto3019336356"></a> **PUT** `/v1/weblivegraphics/layers/keyer/item/setup?keyerLabel=<String>&itemLabel=<String>`

setItemSetup

```javascript
Request body data: {
    setup: {

    }
}
```

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: setItemSetup](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#193)

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

[Go to the top](#top) &bull; [LayersAPI :: createItem](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#166)

## <a name="goto1772645336"></a> **DELETE** `/v1/weblivegraphics/layers/keyer/item?keyerLabel=<String>&itemLabel=<String>`

deleteItem

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: deleteItem](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#176)

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

[Go to the top](#top) &bull; [LayersAPI :: getItem](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#159)

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

[Go to the top](#top) &bull; [LayersAPI :: moveKeyer](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#212)

## <a name="goto3661132383"></a> **PUT** `/v1/weblivegraphics/layers/keyer/pgm?label=<String>&active=<boolean>`

switchKeyerPgm

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchKeyerPgm](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#128)

## <a name="goto63412551"></a> **PUT** `/v1/weblivegraphics/layers/keyer/pvw?label=<String>&active=<boolean>`

switchKeyerPvw

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: switchKeyerPvw](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#136)

## <a name="goto3057591294"></a> **DELETE** `/v1/weblivegraphics/layers/keyer?label=<String>`

deleteKeyer

```javascript
Response: "application/json" {
}
```

[Go to the top](#top) &bull; [LayersAPI :: deleteKeyer](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#121)

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

[Go to the top](#top) &bull; [LayersAPI :: getKeyer](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#105)

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

[Go to the top](#top) &bull; [LayersAPI :: createKeyer](https://github.com/mediaexmachina/weblivegraphics/blob/master/src/main/java/media/mexm/weblivegraphics/controller/LayersAPI.java#115)

