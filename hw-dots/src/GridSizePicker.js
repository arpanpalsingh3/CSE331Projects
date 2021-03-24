/*
 * Copyright ©2019 Dan Grossman.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

/**
 * Props:
 *
 * onChange - a listener for when the size text area has a keyboard event
 * value - the value to display in the text area
 */
class GridSizePicker extends Component {
    render() {
        // IntelliJ might complain about "this.props.onChange" not existing.
        // Don't worry, inside <App /> we're passing something in as an onChange prop, so it exists.
        // IntelliJ just isn't quite smart enough to understand how props work in React.
        return (
            <div id="grid-size-picker">
                <label>
                    Grid Size:
                    <input
                        value={this.props.value}
                        onChange={this.props.onChange}
                        type="number"
                        min={1}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
