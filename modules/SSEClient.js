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

export class SSEClient extends Component {
    constructor(props) {
        super(props);
        this.state = { eventSource: null, hasFirstFetchLayers: false };
    }

    componentDidMount() {
        const eventSource = new EventSource(location.protocol + "/sse", {
            withCredentials: true,
        });
        eventSource.onmessage = this.onMessage.bind(this);
        eventSource.onopen = () => {
            this.setState({ eventSource: eventSource });
            if (this.state.hasFirstFetchLayers == false) {
                fetch(
                    location.protocol +
                        "/v1/weblivegraphics/front/refresh-layers"
                );
                this.setState({ hasFirstFetchLayers: true });
            }
        };
    }

    componentWillUnmount() {
        const eventSource = this.state.eventSource;
        if (eventSource != null) {
            eventSource.close();
        }
    }

    onMessage(event) {
        const eventData = JSON.parse(event.data);
        if (eventData.hasOwnProperty("layers")) {
            this.props.onLayersUpdate(eventData.layers);
        }
    }

    render() {
        return null;
    }
}
