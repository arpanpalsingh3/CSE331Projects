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

/*
 * A Textfield that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */

import React, {Component} from 'react';

/**
 * Props:
 *
 * onChange - a listener for when the edge text area has a keyboard event
 * value - the value to display in the text area
 */
class EdgeList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 'Please write your edges here: (x1,y1 x2,y2 color)'
        };
    };

    handleChange= (event) =>{
        this.setState({ value: event.target.value });
    };

    onDrawClick = () => {
        this.props.onEdgeChange(this.state.value);
    };

    onClearClick = () => {
        this.props.onClearEdge();
    };

    onClearText = () => {
        this.setState({
            value : ''
        });
    };

    onSurpriseClick = () => {
        this.props.onSurprise();
    };

    onColorClick = () => {
        this.props.onColor();
    };

    onTests = () => {
        this.props.onTests();
    };

    render() {
        // IntelliJ might complain about "this.props.onChange" not existing.
        // Don't worry, inside <App /> we're passing something in as an onChange prop, so it exists.
        // IntelliJ just isn't quite smart enough to understand how props work in React.
        return (
            // i want to record the text given to me, store it, and when the draw or clear buttons
            // are used, I want to use that information
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.handleChange}
                    value={this.state.value}
                /> <br/>

                <button onClick={this.onDrawClick}>Draw</button>

                <button onClick={this.onClearClick}>Clear</button>

                <button onClick={this.onClearText}>Clear Text</button>
                <br/>
                <button onClick={this.onSurpriseClick}>Surprise</button>

                <button onClick={this.onColorClick}>Colors</button>

                <button onClick={this.onTests}>HW Tests</button>

            </div>
        );
    }
}

export default EdgeList;
