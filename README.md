# WebLiveGraphics for Media Ex Machina's live streams

Display live informations and events (from external sites, like YouTube or Twitch)
on Media ex Machina's live streams via some special web page.
This web page be will be able to be put as "Web source" on OBS Studio.

Live streams can be see (in french) on [YouTube](https://mexm.media/youtube) and [Twitch](https://www.twitch.tv/mediaexmachina/).

_It's still in alpha._

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics)
[![CodeQL](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml)
[![Java/javascript CI](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/test-sonar.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/test-sonar.yml)

## Technical stack

Front is on Node 17+ JS/NPM/Webpack/React (in admin pages).

Back is on Java 11/Spring Boot/Maven.

## Configuration

You need to setup some configuration files.

- `application.yml` for main configuration.
You'll found an example on `config-dev` directory.
See below for more details.
- `log4j2.xml` for log configurations.
You'll found an example on `config-dev` directory.
Setup as you want, nothing special here.
- `vendor.properties` will contain secrets and special
file references for setup the application, across updates.
It will be created if not found.
This file path will be setup in `application.yml`.

### application.yml

Configuration keys and usage for application.yml, in addition to standard Spring Boot options.

```yml
server:
    address: 0.0.0.0
    port: 8080
```

You should setup address and port to listen connections from web server.

```yml
weblivegraphics:
    vendorProps: /path/to/vendor.properties
    baseBackgroundFile: /path/to/background.png
```

Specific weblivegraphics configuration keys, mandatory.

`weblivegraphics.vendorProps` for the vendor.properties file to point and create if needed.

`weblivegraphics.baseBackgroundFile` for put default background file (png, transparent, 16/9 file).

```yml
js:
    devmode: true | false
```

Active dev mode for backend web server, optional, false by default.

See below for usage; basically for start web app with dev JS files or prod files)

## Roadmap

Actual roadmap can be found on GitHub [Projects](https://github.com/mediaexmachina/weblivegraphics/projects) tab.

Work in progress can be tracked on [enhancement opened Issues](https://github.com/mediaexmachina/weblivegraphics/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

This project is targeted, for the moment,
to animate specifically the Media ex Machina video lives.
Maybe on day it will be transformed to a polyvalent tool,
maybe a framework, but this not the current target.

Free feel to fork it, and maybe to upstream contributions on this way.

## Development Documentation

<details>
<summary>Show information</summary>

### Setup Java

- JDK/JRE 11
- Maven

For build a self-run jar file :

```bash
mvn package
```

The file will be builded on `target/weblivegraphics-X.Y.Z.jar`.

You should build **before** the production-compatible front with `npm install`.
It will be added on the jar. See below.

### Test Java

Just run:

```bash
mvn test
```

### Setup JS

Install Node 17+ from [Nodesource](https://github.com/nodesource/distributions/blob/master/README.md#debinstall):

Example for Debian likes distributions:

```bash
curl -fsSL https://deb.nodesource.com/setup_17.x | bash -
apt-get install -y nodejs
```

And compile:

```bash
npm install --save-dev webpack
npm install --save-dev webpack-cli
npm install compression-webpack-plugin --save-dev
npm install # Prepare production script
npm run watch # Prepare dev script
```

Spring Boot configuration (`application.yml` file)
can choose between prod JS bundle _or_ dev mode.

Switch with:

```yml
js:
    devmode: true | false
```

`js.devmode` is not mandatory.

### Test JavaScript

Just run:

```bash
npm run test
```

You don't need a valid/running/configured Java application to run JS tests.

You'll found JS test files in `src/test/js`.

### Run Java application from JS environment

Just run:

```bash
npm run back
```

For start Spring Boot app via maven in "js dev mod".
Configuration files will be founded on `src/test/js/back-config`.

</details>

## Contribution

Pull requests are welcome.

Some rules must to be followed:

- PR must pass all internal test
- Code should be tested at it's best
- Please use a code linter for Java and JavaScript.
I can provide Eclipse linter configuration.
Free feel to propose one for JS.
- Security and stability is most important than new functionalities,
and always must be prioritized.
- Code quality is the third priority before new functionalities,
in particular for the production of a visible, broadcasted, video live stream.
