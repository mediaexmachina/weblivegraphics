# weblivegraphics

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
