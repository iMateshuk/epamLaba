import React, { Component } from 'react';

import './App.css';
import Header from './components/Header';
import Footer from "./components/Footer";
import AppRouter from "./components/AppRouter";


export default class App extends Component {
    render() {
        return (
            <div>
                <AppRouter />
                <Footer />
            </div >
        );
    }
}