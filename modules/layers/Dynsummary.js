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
import crc32 from "crc/crc32";

export class Dynsummary extends Component {
    constructor(props) {
        super(props);
        this.state = { currentSummary: [], currentChapter: "" };
    }

    componentDidMount() {
        this.props.globalAccess.registerCallback(
            "dynamicalSummary",
            this.onChangeSummary.bind(this)
        );
        this.props.globalAccess.registerCallback(
            "dynamicalSummaryChapter",
            this.onChangeChapter.bind(this)
        );
    }

    onChangeSummary(message) {
        this.setState({ currentSummary: message });
    }

    onChangeChapter(message) {
        this.setState({ currentChapter: message });
    }

    componentWillUnmount() {
        this.props.globalAccess.unRegisterCallback("dynamicalSummary");
        this.props.globalAccess.unRegisterCallback("dynamicalSummaryChapter");
    }

    render() {
        const currentSummary = this.state.currentSummary;
        const currentChapter = this.state.currentChapter;
        const style = {
            backgroundImage: "url(" + liveDynSummaryChaptersBoxURL + ")",
        };

        if (currentSummary.length > 0) {
            const allLines = currentSummary.map((i) => {
                if (currentChapter == i) {
                    return <li key={crc32(i).toString(16)}>Selected: {i}</li>;
                }
                return <li key={crc32(i).toString(16)}>{i}</li>;
            });
            return (
                <ul className="dynsummary full-summary" style={style}>
                    {allLines}
                </ul>
            );
        }

        if (currentChapter != "") {
            return (
                <article className="dynsummary chapter" style={style}>
                    {currentChapter}
                </article>
            );
        }
        return null;
    }
}
