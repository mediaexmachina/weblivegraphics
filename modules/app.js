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
"use strict";

import React, { Component } from "react";
import { render as _render } from "react-dom";

import css from "./app.scss"; //NOSONAR S1128
import { SSEClient } from "./SSEClient";
import { OutputLayers } from "./layers/OutputLayers";
import { BasePage } from "./BasePage";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    onLayersUpdate(layers) {
        this.setState({ lastLayers: layers });
    }

    render() {
        const lastLayers = this.state.lastLayers;
        if (typeof lastLayers == "undefined") {
            return (
                <SSEClient onLayersUpdate={this.onLayersUpdate.bind(this)} />
            );
        }
        let mainPage = null;
        if (pagekind != null) {
            mainPage = <OutputLayers lastLayers={lastLayers} />;
        } else {
            mainPage = <BasePage />;
        }

        return (
            <>
                <React.StrictMode>
                    {mainPage}
                    <SSEClient
                        onLayersUpdate={this.onLayersUpdate.bind(this)}
                    />
                </React.StrictMode>
            </>
        );
    }
}

_render(<App />, document.getElementById("react"));
