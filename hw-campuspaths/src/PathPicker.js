import React, {Component} from 'react';

class PathPicker extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "Please enter your building names, separated by commas\n" +
                "example: building1,building2"
        };
    };

    handleChange= (event) =>{
        this.setState({ value: event.target.value });
    };

    onDrawClick = () => {
        if (this.state.value === "") {
            alert("Provided text box for entry was empty")
        }
        this.props.onPathRequest(this.state.value);
    };

    onClearText = () => {
        this.setState({
            value : ''
        });
    };

    onClearClick = () => {
        this.setState({
            value: ''
        });
        this.props.onPathRequest('');
    };

    render() {
        return (
            <div id="path-picker">
                Path <br/>
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

            </div>
        );
    }
}
export default PathPicker;


