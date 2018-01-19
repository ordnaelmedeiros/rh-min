import React, { Component } from 'react';
import {Link} from 'react-router';

export default class App extends Component {
    render() {
        return (
            <div>
                Hello World
                <br/>
                <br/>
                <Link to="/" >Home</Link>
                <br/>
                <Link to="/page2" >Page2</Link>
                <br/>
                <br/>
                
                <div>
                    {this.props.children}
                </div>

            </div>
        )
    }
}