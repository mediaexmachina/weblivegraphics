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
import { Client } from '@stomp/stompjs';

export class StompClient extends Component {
    constructor(props) {
        super(props);

        let secureTag = "";
        if (location.protocol == "https:") {
            secureTag = "s";
        }

        this.stompClient = new Client({
            brokerURL: 'ws' + secureTag + '://' + location.host + '/ws',
            debug: function (str) {
                console.debug(str);
            },
        });
        this.stompClient.onConnect = this.onConnect.bind(this);
        this.stompClient.onStompError = this.onStompError.bind(this);
    }

    componentDidMount() {
        this.stompClient.activate();
    }

    componentWillUnmount() {
        this.state.subscriptions
            .forEach(sub => sub.unsubscribe());
        this.stompClient.deactivate();
    }

    onConnect(frame) {
        this.setState({
            "subscriptions": [
                this.stompClient.subscribe("/topic/layers", this.props.onLayersUpdate)
            ]
        });
        fetch(location.protocol + '/v1/weblivegraphics/front/refresh-layers');
    }

    onStompError(frame) {
        console.error('Stomp error', frame);
    }

    render() {
        return null;
    }
}

//this.stompClient.publish({
//    destination: '/app/hello',
//    body: JSON.stringify({"name": "AAA0"})
//});
