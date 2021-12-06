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
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
    entry: './src/main/js/app.js',
    devtool: 'source-map',
    cache: true,
    mode: 'development',
    plugins: [
        new MiniCssExtractPlugin({
            filename: "target/classes/static/[name].css",
            chunkFilename: "target/classes/static/[id].css"
        })],
    watch: true,
    watchOptions: {
        aggregateTimeout: 200,
        poll: 1000
    },
    output: {
        path: __dirname,
        filename: 'target/classes/static/bundle.js'
    },
    module: {
        rules: [{
            test: path.join(__dirname, '.'),
            exclude: /(node_modules)/,
            use: [{
                loader: 'babel-loader',
                options: {
                    presets: [
                        "@babel/preset-env",
                        ["@babel/preset-react", { "runtime": "automatic" }]
                    ]
                }
            }]
        }, {
            test: /\.s[ac]ss$/i,
            use: [
                MiniCssExtractPlugin.loader,
                "css-loader",
                {
                    loader: "sass-loader",
                    options: {
                        warnRuleAsWarning: true,
                    },
                }
            ]
        }]
    }
};
