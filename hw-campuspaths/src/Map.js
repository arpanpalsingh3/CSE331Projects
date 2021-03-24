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
import "./Map.css";

class Map extends Component {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    constructor(props) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    componentDidUpdate() {
        // Might want something here too...
        this.drawBackgroundImage();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        let ctx = canvas.getContext("2d");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        if (this.props.route != null) {
      //      console.log(this.props.route.path[0].start)
            let route = this.props.route;
            this.drawRoute(ctx, route)
        }
    }

    drawRoute = (ctx, route ) => {
        if (route !== false) {
            let routeArr = route.path;
            let len = routeArr.length;
            let ticker = 0;
            // go through array and draw each path
            do {
                ctx.lineWidth = 10;
                ctx.strokeStyle = 'red';
                ctx.beginPath();
                ctx.moveTo(routeArr[ticker].start.x, routeArr[ticker].start.y);
                ctx.lineTo(routeArr[ticker].end.x, routeArr[ticker].end.y);
                ctx.stroke();
                ticker++;
            } while (ticker !== len);
        }
    };



    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default Map;