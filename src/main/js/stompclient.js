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
'use strict';

import { Component } from 'react';
import { Client } from '@stomp/stompjs';

class StompClient extends Component {

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
		this.state.subscription.unsubscribe();
		this.stompClient.deactivate();
	}

	onConnect(frame) {
		const subscription = this.stompClient.subscribe("/topic/greetings",
			this.onStompMessage.bind(this));
		console.log(subscription);
		this.setState({
			"subscription": subscription
		});

		this.stompClient.publish({
			destination: '/app/hello',
			body: JSON.stringify({"name": "AAA0"})
		});
	}

	onStompError(frame) {
		console.error('Stomp error', frame);
	}

	onStompMessage(message) {
		console.log("message R", JSON.parse(message.body));
	}

	render() {
		return null;
	}

}

export { StompClient };