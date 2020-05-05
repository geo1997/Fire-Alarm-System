import React from 'react';

import './App.css';
import Login from "./Login";
import {BrowserRouter as Router, Route} from 'react-router-dom';
import Sensor from "./Sensor";

function App() {
  return (
    <Router> 
      {/* path routes to different components */}
        <div className="App">
            <Route path="/" exact component={Login} />
            <Route path="/sensor" exact component={Sensor} />
        </div>
    </Router>
  );
}

export default App;
