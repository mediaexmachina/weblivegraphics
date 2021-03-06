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

export class Clock extends Component {
    constructor(props) {
        super(props);
        this.state = { now: this.computeClock() };
    }

    componentDidMount() {
        const intervalRef = window.setInterval(
            this.onTimeClock.bind(this),
            1000
        );
        this.setState({ intervalRef: intervalRef });
    }

    computeClock() {
        const options = { hour: "2-digit", minute: "2-digit" };
        return new Date().toLocaleTimeString(undefined, options);
    }

    onTimeClock() {
        this.setState({ now: this.computeClock() });
    }

    componentWillUnmount() {
        window.clearInterval(this.state.intervalRef);
    }

    render() {
        return <div className="clock">{this.state.now}</div>;
    }
}
