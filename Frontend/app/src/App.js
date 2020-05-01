import React from 'react';
import logo from './logo.svg';
import './App.css';
import Login from "./Login";
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Sensor from "./Sensor";

function App() {
  return (
    <Router>
        <div className="App">
            <Route path="/" exact component={Login} />
            <Route path="/sensor" exact component={Sensor} />
        </div>
    </Router>
  );
}

export default App;
