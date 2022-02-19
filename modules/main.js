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
 * Copyright (C) Media ex Machina 2022
 *
 */
"use strict";

import React, { Component } from "react";
import { StompClient } from "./StompClient";
import { OutputLayers } from "./OutputLayers";
import { BasePage } from "./BasePage";

export class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    onLayersUpdate(message) {
        const lastLayers = JSON.parse(message.body);
        this.setState({"lastLayers": lastLayers});
    }

    render() {
        const lastLayers = this.state.lastLayers;
        if (typeof lastLayers == "undefined") {
            return <StompClient onLayersUpdate={this.onLayersUpdate.bind(this)} />;
        }
        let mainPage = null;
        if (pagekind != null) {
            mainPage = <OutputLayers lastLayers={lastLayers} />;
        } else {
            mainPage = <BasePage />;
        }

        return (
            <div>
                {mainPage}
                <StompClient onLayersUpdate={this.onLayersUpdate.bind(this)} />
            </div>
        );
    }
}
