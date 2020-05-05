import React, {Component} from 'react';
import * as ReactDOM from 'react-dom';
import {
    RadialGauge
} from '@progress/kendo-react-gauges';
import '@progress/kendo-theme-default/dist/all.css';
import Logo from './images/navicon.png';
import {Link} from 'react-router-dom';


class Sensor extends Component {
    //initilaize the state of the variables
    state = {
        isLoading: true,
        alarms: [],
        value: 0,
        count:40
    }
    intervalID;
    countID;

    async componentDidMount() {
        /*when conponentDidMount executes run getData method and setInterval to 4 seconds
            to automatically retrieve data using the REST api
        */
        await this.getData();
        
        this.intervalID = setInterval(this.getData.bind(this), 40000);
        setInterval( () =>{
            this.setState({
                value:this.state.value
            })
        },0);

        //every 40 seconds initilaize countdown
        this.getTimer();
        setInterval( () =>{
            this.setState({
                count:40
            })
        },40000);
       
        
    }
    //countdown implementation
        getTimer(){
            this.myInterval = setInterval(() =>{
                this.setState(prevState =>({
                    count:this.state.count -1
                    
                }))
            },1000)
           
           
        }

        //safetly clear the interval
    componentWillMount(){
        clearInterval(this.myInterval)
    }

    //method to fetch the alarms through the REST api
    async getData(){
        const response = await fetch('/alarms')
        const body = await response.json();
        this.setState({alarms : body,isLoading: false});
    }

   
    //user interface for the Sensor component
    render() {
        const {count}= this.state;
        const {alarms,isLoading} = this.state;
        if (isLoading)
            return (<div><h3 className="text-dark">Loading...</h3></div>)
        const radialOptions = {
            pointer: {
                value: this.state.value
            }
        };



        return (
            <div>
                <nav className="navbar navbar-light bg-dark mb-2">
                    <div className="navbar-brand text-light">
                        <img src={Logo} width="30" height="30" className="d-inline-block align-top mr-2" alt=""/>
                              Fire Alarm System
                    </div>
                    <ul className="navbar-nav">
                        <li className="nav-item active">
                            <Link to={'/'} className="nav-link text-light" href="#">Log out <span className="sr-only">(current)</span></Link>
                        </li>
                    </ul>
                </nav>
              
                <div className="text-danger mt-4 mb-1 text-center">*New data will be fetched every 40 seconds </div>
                <div className="text-danger mb-4 text-center">Timer: {count} seconds left</div>
                <div className="row ml-3 mr-3">
                    {
                        alarms.map(alarm =>
                            <div className="col-sm-4 mb-4">
                                <div className="card">
                                    <div className="card-body" id={alarm.id}>
                                        <h3 className="card-title text-center">Sensor {alarm.id}</h3>
                                        <h5><span className="mr-2">Floor No.</span> : <span className="ml-2">{alarm.floorNum}</span></h5>
                                        <h5><span className="mr-2">Room No.</span>: <span className="ml-2">{alarm.roomNum}</span></h5>
                                        <div className="w-50 mb-2" style={{marginLeft:'25%'}}>
                                            <RadialGauge {...{value:alarm.co2level*10}}/>
                                            {alarm.co2level>5 ? <div className="text-center text-danger">CO<small>2</small> Level : {alarm.co2level}</div> :
                                                <div className="text-center text-success">CO<small>2</small> Level : {alarm.co2level}</div>}
                                        </div>
                                        <div className="w-50" style={{marginLeft:'25%'}}>
                                            <RadialGauge {...{value:alarm.smokeLevel*10}}/>
                                            {alarm.smokeLevel>5 ? <div className="text-center text-danger">Smoke Level : {alarm.smokeLevel}</div> :
                                                <div className="text-center text-success">Smoke Level : {alarm.smokeLevel}</div>}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )}
                </div>
            </div>
        );
    }
}



export default Sensor;
