/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import Map from "./Map";
import PathPicker from "./PathPicker"
class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            route: null
        };
    }

    PathRequest = async (event) => {
        if (event !== "") {
            let splitArr = event.split(',');
            let start = splitArr[0];
            let end = splitArr[1];

            let response = await fetch("http://localhost:4567/campuspath?start=" + start + "&end=" + end);

            if (!response.ok) {
                alert("Your provided building names are inaccurate. Please check your spelling and spaces. Provided buildings" +
                    "must be in following format: \n " +
                    "(building1,building2), without parenthesis");
            }

            let object = await response.json();
            this.setState({
                route: object
            });
        }
        else {
            this.setState({
                route: false
            })
        }
    };

    render() {
        return (
            <div>
                <p id="app-title">UW CampusMap!</p>
                <Map route={this.state.route}/>
                <PathPicker onPathRequest={this.PathRequest}/>
            </div>

        );
    }

}

export default App;
