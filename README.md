# weblivegraphics

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics) 
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mediaexmachina_weblivegraphics&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=mediaexmachina_weblivegraphics) 
[![CodeQL](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/codeql-analysis.yml) 
[![Java CI with Maven](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/maven-package.yml/badge.svg)](https://github.com/mediaexmachina/weblivegraphics/actions/workflows/maven-package.yml)

## JS Doc

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
