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
        this.state = { topicCallbackers: {} };
    }

    onLayersUpdate(layers) {
        this.setState({ lastLayers: layers });
    }

    onRegisterCallback(topic, onMessage) {
        this.setState((state) => {
            let newTopicCallbackers = Object.assign({}, state.topicCallbackers);
            newTopicCallbackers[topic] = onMessage;
            return { topicCallbackers: newTopicCallbackers };
        });
    }

    onUnRegisterCallback(topic) {
        this.setState((state) => {
            let newTopicCallbackers = Object.assign({}, state.topicCallbackers);
            newTopicCallbackers.delete(topic);
            return { topicCallbackers: newTopicCallbackers };
        });
    }

    render() {
        const lastLayers = this.state.lastLayers;
        const globalAccess = {
            registerCallback: this.onRegisterCallback.bind(this),
            unRegisterCallback: this.onUnRegisterCallback.bind(this),
        };

        if (typeof lastLayers == "undefined") {
            return (
                <SSEClient
                    onLayersUpdate={this.onLayersUpdate.bind(this)}
                    topicCallbackers={this.state.topicCallbackers}
                />
            );
        }
        let mainPage = null;
        if (pagekind != null) {
            mainPage = (
                <OutputLayers
                    lastLayers={lastLayers}
                    globalAccess={globalAccess}
                />
            );
        } else {
            mainPage = <BasePage />;
        }

        return (
            <>
                <React.StrictMode>
                    {mainPage}
                    <SSEClient
                        onLayersUpdate={this.onLayersUpdate.bind(this)}
                        topicCallbackers={this.state.topicCallbackers}
                    />
                </React.StrictMode>
            </>
        );
    }
}

_render(<App />, document.getElementById("react"));
