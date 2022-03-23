import React, {Component} from 'react';

import './App.css';
import AppRouter from "./components/AppRouter";


export default class App extends Component {
    render() {
        return (
            <div>
                <AppRouter/>
            </div>
        );
    }
}