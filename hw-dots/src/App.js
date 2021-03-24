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

import React, {Component} from 'react';
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            gridSize: 4,  // The number of points in the grid
            edges: [],
            edgeBool: false
        };
    }

    updateGridSize = (event) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below:
        if (parseInt(event.target.value) > 100) {
            alert("Value must be between 0 and 100(inclusive)")
        } else {
            this.setState({
                gridSize: parseInt(event.target.value)
            });
        }

    };

    EdgeChange = (event) => {
        /* take what I get from EdgeList onDrawClick
        and parse it and put it into edges
         */
        let enterSplitArray = event.split('\n');
        let bam = '';
        for (let i = 0; i < enterSplitArray.length; i++) {
            if (enterSplitArray[i].toString() !== "") {
                bam = bam + enterSplitArray[i].toString();
            }
            if (i+1 < enterSplitArray.length) {
                bam = bam + ' '
            }
        }
        let spaceSplitArray = bam.split(' ');
        if (spaceSplitArray.length % 3 !== 0) {
            alert("Must be in format x1,y1 x2,y2 color");
        } else {
            this.setState({
                edges: bam.split(' '),
                edgeBool: true
            });
        }
    };

    ClearEdge = () => {
        this.setState( {
            edges: [],
        })
    };

    Surprise = () => {
        let x = "25,25 30,30 red\n" +
            "75,25 70,30 red\n" +
            "50,40 50,45 red\n" +
            "20,70 30,80 red\n" +
            "30,80 70,80 red\n" +
            "70,80 80,70 red";
        this.setState({
            gridSize : 100
        });
        this.EdgeChange(x);
    };

    Color = () => {
        let x = "0,0 100,100 blue\n" +
            "100,0 0,100 red\n" +
            "50,0 50,100 green\n" +
            "0,50 100,50 purple\n" +
            "25,0 75,100 yellow\n" +
            "75,0 25,100 orange\n" +
            "0,25 100,75 pink\n" +
            "100,25 0,75 black";
        this.setState({
            gridSize : 100
        });
        this.EdgeChange(x);
    };

    Test = () => {
        let x = "82,46 83,46 red\n" +
            "83,46 84,47 red\n" +
            "84,47 84,48 red\n" +
            "84,48 83,49 red\n" +
            "83,49 82,49 red\n" +
            "82,49 81,48 red\n" +
            "81,48 81,47 red\n" +
            "81,47 82,46 red\n" +
            "82,46 81,44 orangered\n" +
            "83,46 84,44 orangered\n" +
            "84,47 86,46 orangered\n" +
            "84,48 86,49 orangered\n" +
            "83,49 84,51 orangered\n" +
            "82,49 81,51 orangered\n" +
            "81,48 79,49 orangered\n" +
            "81,47 79,46 orangered";

        this.setState({
            gridSize : 100
        });
        this.EdgeChange(x);
    };


    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={this.state.gridSize} onChange={this.updateGridSize}/>
                <Grid size={this.state.gridSize} edges={this.state.edges} edgeBool={this.state.edgeBool} width={canvas_size} height={canvas_size} />
                <EdgeList onEdgeChange={this.EdgeChange} onClearEdge={this.ClearEdge} onSurprise={this.Surprise} onColor={this.Color} onTests={this.Test}/>
            </div>

        );
    }

}

export default App;
