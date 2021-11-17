/*
 * This file is part of weblivegraphics.
 *
 * weblivegraphics is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * weblivegraphics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * Copyright (C) Media ex Machina 2021
 *
 */
var path = require('path');

const TerserPlugin = require('terser-webpack-plugin');
const LicenseCheckerWebpackPlugin = require("license-checker-webpack-plugin");

module.exports = {
    entry: './src/main/js/app.js',
    cache: true,
    mode: 'production',
    plugins: [
        new LicenseCheckerWebpackPlugin({
        outputFilename: "./THIRD-PARTY-JS.txt",
    })],
    optimization: {
        minimizer: [
            new TerserPlugin({
            extractComments: false,
        })],
    },
    output: {
        path: __dirname,
        filename: 'src/main/resources/static/bundle-prod.js'
    },
    module: {
        rules: [{
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};
