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

/* A simple grid with a variable size */
/* Most of the assignment involves changes to this class */

import React, {Component} from 'react';
/**
 * Props:
 *
 * width - the desired width of the grid area
 * height - the desired height of the grid area
 * size - the number of points along a single axis in the grid
 */
class Grid extends Component {
    constructor(props) {
        super(props);
        this.state = {
            backgroundImage: null,  // An image object to render into the canvas.
            badEdge: false
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background = new Image();
        background.onload = () => {
            let newState = {
                backgroundImage: background
            };
            this.setState(newState);
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {
        let ctx = this.canvasReference.current.getContext('2d');
        ctx.clearRect(0, 0, this.props.width, this.props.height);
        // Once the image is done loading, it'll be saved inside our state.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        // Draw all the dots.
        let coordinates = this.getCoordinates();
        for(let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }
        if (this.props.edgeBool) {
            this.drawLine(ctx, this.props.edges)
        }
    };

    getCoordinates = () => {
        let grid = [];
        let gridBreak = (500/(this.props.size+1));
        let rows = this.props.size;
        let columns = this.props.size;
        let ticker = 0;
        for ( let i = 1; i <= columns; i++) {
            for (let j = 1; j <= rows; j++) {
                grid[ticker] = [(i*gridBreak),(j*gridBreak)];
                ticker++;
            }
        }
        return grid;
    };

    drawCircle = (ctx, coordinate) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        let radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    drawLine = (ctx, edgeArray) => {
        // draw a line from starting coordinate to ending coordinate
        // if coordinate is (1,2) then multiply 1 by (500/(this.props.size+1)) and 2 by (500/(this.props.size+1))
        // to get the coordinates in respect to the actual graph
        let max = 0;
        let badEdge = false;
        for (let i = 0; i < edgeArray.length; i = i+3) {
            let cord = edgeArray[i].split(',');
            let cord2 = edgeArray[i + 1].split(',');
            if (cord.length % 2 !== 0 || !cord2.length % 2 !== 0) {
                alert("Incorrect formatting! Make sure its: \n" +
                    "x1,y1 x2,y2 color")
            } else {
                let x1 = parseInt(cord[0]);
                let y1 = parseInt(cord[1]);
                let x2 = parseInt(cord2[0]);
                let y2 = parseInt(cord2[1]);
                if (x1 > this.props.size || y1 > this.props.size || x2 > this.props.size || y2 > this.props.size) {
                    badEdge = true;
                    if (Math.max(x1, x2, y1, y2) > max)
                        max = Math.max(x1, x2, y1, y2);
                } else {
                    let cordArr1 = [x1 * (500 / (this.props.size + 1)), y1 * (500 / (this.props.size + 1))];
                    let cordArr2 = [x2 * (500 / (this.props.size + 1)), y2 * (500 / (this.props.size + 1))];
                    ctx.strokeStyle = edgeArray[i + 2];
                    ctx.lineWidth = 2;
                    ctx.beginPath();
                    ctx.moveTo(cordArr1[0], cordArr1[1]);
                    ctx.lineTo(cordArr2[0], cordArr2[1]);
                    ctx.stroke();
                }
            }
            if (badEdge) {
                alert("Grid must be at least size " + max + " to draw this specific edge")
            }
        }
    };


    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
