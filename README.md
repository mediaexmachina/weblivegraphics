# weblivegraphics

Display live informations and events on your live stream via web pages.

Front is on Javascript/NPM/React.

Back is on Java 11/Spring Boot

_It's still in alpha._

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics) 
[![CodeQL](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml) 
[![Java/javascript CI](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/test-sonar.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/test-sonar.yml)

## Java Documentation

- JDK/JRE 11
- Maven

For build a self-run jar file :

```bash
mvn package
```

The file will be builded on `target/weblivegraphics-X.Y.Z.jar`.

You should build **before** the production-compatible front with `npm install`. It will be added on the jar. See below.

## JS Documentation

### Setup JS

Install Node 17+ from [Nodesource](https://github.com/nodesource/distributions/blob/master/README.md#debinstall):

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

Spring Boot configuration (`application.yml` file) can choose between prod JS bundle _or_ dev mode.
Switch with:

```yml
js:
    devmode: true | false
```

`js.devmode` is not mandatory.

### Test JS

Just run:

```bash
npm run test
```

You don't need a valid/running/configured Java application to run JS tests.

You'll found JS test files in `src/test/js`.
